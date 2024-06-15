package com.fatec.zl.amos.trabalhosemestral;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.zl.amos.trabalhosemestral.controller.ComprasMercadoController;
import com.fatec.zl.amos.trabalhosemestral.model.ComprasMercado;
import com.fatec.zl.amos.trabalhosemestral.persistence.ComprasMercadoDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class comprasMercado extends Fragment {

    private View view;
    private EditText etDataMercado, etValorMercado, etNomeMercado, etIDCompraMercado, etQuantidadeMercado;
    private TextView tvResMercado;
    private Button btnCadastrarMercado, btnListarMercado, btnExcluirMercado, btnConsultrarMercado, btnAtualizarMercado;

    private ComprasMercadoController mcont;
    private DateTimeFormatter dateFormatter;

    public comprasMercado() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_compras_mercado, container, false);

        // Initialize EditText fields
        etDataMercado = view.findViewById(R.id.etDataMercado);
        etValorMercado = view.findViewById(R.id.etValorMercado);
        etNomeMercado = view.findViewById(R.id.etNomeMercado);
        etIDCompraMercado = view.findViewById(R.id.etIDCompraMercado);
        etQuantidadeMercado = view.findViewById(R.id.etQuantidadeMercado);

        // Initialize Buttons
        btnCadastrarMercado = view.findViewById(R.id.btnCadastrarMercado);
        btnAtualizarMercado = view.findViewById(R.id.btnAtualizarMercado);
        btnExcluirMercado = view.findViewById(R.id.btnExcluirMercado);
        btnListarMercado = view.findViewById(R.id.btnListarMercado);
        btnConsultrarMercado = view.findViewById(R.id.btnConsultarMercado);

        // Initialize TextView
        tvResMercado = view.findViewById(R.id.tvResMercado);
        tvResMercado.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResMercado.setMovementMethod(new ScrollingMovementMethod());

        // Initialize Controller
        mcont = new ComprasMercadoController(new ComprasMercadoDao(view.getContext()));

        // Initialize Date Formatter
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Set button listeners
        btnCadastrarMercado.setOnClickListener(op -> acaoCadastrar());
        btnAtualizarMercado.setOnClickListener(op -> acaoAtualizar());
        btnListarMercado.setOnClickListener(op -> acaoListar());
        btnConsultrarMercado.setOnClickListener(op -> acaoConsultar());
        btnExcluirMercado.setOnClickListener(op -> acaoExcluir());

        return view;
    }

    private void acaoListar() {
        try {
            List<ComprasMercado> compra = mcont.listar();
            StringBuilder buffer = new StringBuilder();
            for (ComprasMercado c : compra) {
                buffer.append(c.toString()).append("\n");
            }
            tvResMercado.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        ComprasMercado compra = montaComprasMercado();
        try {
            mcont.deletar(compra);
            Toast.makeText(view.getContext(), "COMPRA EXCLUIDA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoConsultar() {
        ComprasMercado compra = montaComprasMercado();
        try {
            compra = mcont.buscar(compra);
            if (compra.getMercado() != null) {
                preencherMercado(compra);
            } else {
                Toast.makeText(view.getContext(), "COMPRA NÃO ENCONTRADA", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoAtualizar() {
        ComprasMercado compra = montaComprasMercado();
        try {
            mcont.modificar(compra);
            Toast.makeText(view.getContext(), "COMPRA ATUALIZADA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoCadastrar() {
        ComprasMercado compra = montaComprasMercado();
        try {
            mcont.inserir(compra);
            Toast.makeText(view.getContext(), "COMPRA CADASTRADA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void limparCampos() {
        etValorMercado.setText("");
        etIDCompraMercado.setText("");
        etQuantidadeMercado.setText("");
        etDataMercado.setText("");
        etNomeMercado.setText("");
    }

    private ComprasMercado montaComprasMercado() {
        ComprasMercado c = new ComprasMercado();

        // Verify if fields are not empty before parsing
        if (!etIDCompraMercado.getText().toString().isEmpty()) {
            c.setIDCompra(Integer.parseInt(etIDCompraMercado.getText().toString()));
        }
        if (!etValorMercado.getText().toString().isEmpty()) {
            c.setValor(Float.parseFloat(etValorMercado.getText().toString()));
        }
        if (!etQuantidadeMercado.getText().toString().isEmpty()) {
            c.setQuantidade(Integer.parseInt(etQuantidadeMercado.getText().toString()));
        }
        if (!etNomeMercado.getText().toString().isEmpty()) {
            c.setMercado(etNomeMercado.getText().toString());
        }
        if (!etDataMercado.getText().toString().isEmpty()) {
            c.setData(LocalDate.parse(etDataMercado.getText().toString(), dateFormatter));
        }

        return c;
    }

    private void preencherMercado(ComprasMercado c) {
        etIDCompraMercado.setText(String.valueOf(c.getIDCompra()));
        etValorMercado.setText(String.valueOf(c.getValor()));
        etQuantidadeMercado.setText(String.valueOf(c.getQuantidade()));
        etDataMercado.setText(c.getData().format(dateFormatter));
        etNomeMercado.setText(c.getMercado());
    }
}

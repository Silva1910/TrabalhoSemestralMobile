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

import com.fatec.zl.amos.trabalhosemestral.controller.ComprasOutrosController;
import com.fatec.zl.amos.trabalhosemestral.model.ComprasOutros;
import com.fatec.zl.amos.trabalhosemestral.persistence.ComprasOutrasDao;

import java.sql.SQLException;
import java.util.List;

public class comprasOutros extends Fragment {

    private View view;
    private EditText etValorOutros, etDescricaoOutros, etIDCompraOutros, etQuantidadeOutros;
    private TextView tvResOutros;
    private Button btnCadastrarOutros, btnListarOutros, btnExcluirOutros, btnConsultrarOutros, btnAtualizarOutros;

    private ComprasOutrosController ocont;

    public comprasOutros() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_compras_outros, container, false);

        // Initialize EditText fields
        etValorOutros = view.findViewById(R.id.etValorCompraOutros);
        etDescricaoOutros = view.findViewById(R.id.etDescricaoOutros);
        etIDCompraOutros = view.findViewById(R.id.etIDCompraOutros);
        etQuantidadeOutros = view.findViewById(R.id.etQuantidadeOutros);

        // Initialize Buttons
        btnCadastrarOutros = view.findViewById(R.id.btnCadastrarOutros);
        btnAtualizarOutros = view.findViewById(R.id.btnAtualizarOutros);
        btnExcluirOutros = view.findViewById(R.id.btnExcluirOutros);
        btnListarOutros = view.findViewById(R.id.btnListarOutros);
        btnConsultrarOutros = view.findViewById(R.id.btnConsultarOutros);

        // Initialize TextView
        tvResOutros = view.findViewById(R.id.tvResOutros);
        tvResOutros.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResOutros.setMovementMethod(new ScrollingMovementMethod());

        // Initialize Controller
        ocont = new ComprasOutrosController(new ComprasOutrasDao(view.getContext()));

        // Set button listeners
        btnCadastrarOutros.setOnClickListener(op -> acaoCadastrar());
        btnAtualizarOutros.setOnClickListener(op -> acaoAtualizar());
        btnListarOutros.setOnClickListener(op -> acaoListar());
        btnConsultrarOutros.setOnClickListener(op -> acaoConsultar());
        btnExcluirOutros.setOnClickListener(op -> acaoExcluir());

        return view;
    }

    private void acaoListar() {
        try {
            List<ComprasOutros> compra = ocont.listar();
            StringBuilder buffer = new StringBuilder();
            for (ComprasOutros c : compra) {
                buffer.append(c.toString()).append("\n");
            }
            tvResOutros.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        ComprasOutros compra = montaComprasOutros();
        try {
            ocont.deletar(compra);
            Toast.makeText(view.getContext(), "COMPRA EXCLUIDA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoConsultar() {
        ComprasOutros compra = montaComprasOutros();
        try {
            compra = ocont.buscar(compra);
            if (compra.getDescricao() != null) {
                preencherOutros(compra);
            } else {
                Toast.makeText(view.getContext(), "COMPRA NÃO ENCONTRADA", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoAtualizar() {
        ComprasOutros compra = montaComprasOutros();
        try {
            ocont.modificar(compra);
            Toast.makeText(view.getContext(), "COMPRA ATUALIZADA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoCadastrar() {
        ComprasOutros compra = montaComprasOutros();
        try {
            ocont.inserir(compra);
            Toast.makeText(view.getContext(), "COMPRA CADASTRADA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void limparCampos() {
        etValorOutros.setText("");
        etIDCompraOutros.setText("");
        etQuantidadeOutros.setText("");
        etDescricaoOutros.setText("");
    }

    private ComprasOutros montaComprasOutros() {
        ComprasOutros c = new ComprasOutros();

        if (!etIDCompraOutros.getText().toString().isEmpty()) {
            c.setIDCompra(Integer.parseInt(etIDCompraOutros.getText().toString()));
        }
        if (!etValorOutros.getText().toString().isEmpty()) {
            c.setValor(Float.parseFloat(etValorOutros.getText().toString()));
        }
        if (!etQuantidadeOutros.getText().toString().isEmpty()) {
            c.setQuantidade(Integer.parseInt(etQuantidadeOutros.getText().toString()));
        }
        if (!etDescricaoOutros.getText().toString().isEmpty()) {
            c.setDescricao(etDescricaoOutros.getText().toString());
        }

        return c;
    }

    private void preencherOutros(ComprasOutros c) {
        etIDCompraOutros.setText(String.valueOf(c.getIDCompra()));
        etValorOutros.setText(String.valueOf(c.getValor()));
        etQuantidadeOutros.setText(String.valueOf(c.getQuantidade()));
        etDescricaoOutros.setText(c.getDescricao());
    }
}

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

import com.fatec.zl.amos.trabalhosemestral.controller.ComprasRoupasController;
import com.fatec.zl.amos.trabalhosemestral.model.ComprasRoupas;
import com.fatec.zl.amos.trabalhosemestral.persistence.ComprasRoupasDao;

import java.sql.SQLException;
import java.util.List;
import android.util.Log;

public class comprasRoupas extends Fragment {
    private View view;
    private EditText etTipoRoupas, etValorRoupas, etTamanhoRoupas, etIDCompraRoupas, etQuantidadeRoupas;
    private TextView tvResRoupas;
    private Button btnCadastrarRoupas, btnListarRoupas, btnExcluirRoupas, btnConsultrarRoupas, btnAtualizarRoupas;
    private ComprasRoupasController rcont;

    public comprasRoupas() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_compras_roupas, container, false);

        // Initialize EditText fields
        etTipoRoupas = view.findViewById(R.id.etTipoRoupas);
        etValorRoupas = view.findViewById(R.id.etValorCompraRoupas);
        etTamanhoRoupas = view.findViewById(R.id.etTamanhoRoupas);
        etIDCompraRoupas = view.findViewById(R.id.etIDRoupas);
        etQuantidadeRoupas = view.findViewById(R.id.etQuantidadeRoupas);

        // Initialize Buttons
        btnCadastrarRoupas = view.findViewById(R.id.btnCadastrarRoupas);
        btnAtualizarRoupas = view.findViewById(R.id.btnAtualizarRoupas);
        btnExcluirRoupas = view.findViewById(R.id.btnExcluirRoupas);
        btnListarRoupas = view.findViewById(R.id.btnListarRoupas);
        btnConsultrarRoupas = view.findViewById(R.id.btnConsultarRoupas);

        // Initialize TextView
        tvResRoupas = view.findViewById(R.id.tvResRoupas);
        tvResRoupas.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResRoupas.setMovementMethod(new ScrollingMovementMethod());

        // Initialize Controller
        rcont = new ComprasRoupasController(new ComprasRoupasDao(view.getContext()));

        // Set button listeners
        btnCadastrarRoupas.setOnClickListener(op -> acaoCadastrar());
        btnAtualizarRoupas.setOnClickListener(op -> acaoAtualizar());
        btnListarRoupas.setOnClickListener(op -> acaoListar());
        btnConsultrarRoupas.setOnClickListener(op -> acaoConsultar());
        btnExcluirRoupas.setOnClickListener(op -> acaoExcluir());

        return view;
    }

    private void acaoListar() {
        try {
            List<ComprasRoupas> compra = rcont.listar();
            StringBuilder buffer = new StringBuilder();
            for (ComprasRoupas c : compra) {
                buffer.append(c.toString()).append("\n");
            }
            tvResRoupas.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        ComprasRoupas compra = montaComprasRoupas();
        Log.d("acaoExcluir", "Compra ID: " + compra.getIDCompra());
        try {
            rcont.deletar(compra);
            Toast.makeText(view.getContext(), "COMPRA EXCLUIDA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoConsultar() {
        ComprasRoupas compra = montaComprasRoupas();
        try {
            compra = rcont.buscar(compra);
            if (compra.getTipoRoupa() != null) {
                preencherRoupas(compra);
            } else {
                Toast.makeText(view.getContext(), "COMPRA NÃO ENCONTRADA", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoAtualizar() {
        ComprasRoupas compra = montaComprasRoupas();
        try {
            rcont.modificar(compra);
            Toast.makeText(view.getContext(), "COMPRA ATUALIZADA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoCadastrar() {
        ComprasRoupas compra = montaComprasRoupas();
        try {
            rcont.inserir(compra);
            Toast.makeText(view.getContext(), "COMPRA CADASTRADA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void limparCampos() {
        etValorRoupas.setText("");
        etIDCompraRoupas.setText("");
        etQuantidadeRoupas.setText("");
        etTipoRoupas.setText("");
        etTamanhoRoupas.setText("");
    }

    private ComprasRoupas montaComprasRoupas() {
        ComprasRoupas c = new ComprasRoupas();

        if (!etIDCompraRoupas.getText().toString().isEmpty()) {
            c.setIDCompra(Integer.parseInt(etIDCompraRoupas.getText().toString()));
        }
        if (!etValorRoupas.getText().toString().isEmpty()) {
            c.setValor(Float.parseFloat(etValorRoupas.getText().toString()));
        }
        if (!etQuantidadeRoupas.getText().toString().isEmpty()) {
            c.setQuantidade(Integer.parseInt(etQuantidadeRoupas.getText().toString()));
        }
        if (!etTipoRoupas.getText().toString().isEmpty()) {
            c.setTipoRoupa(etTipoRoupas.getText().toString());
        }
        if (!etTamanhoRoupas.getText().toString().isEmpty()) {
            c.setTamanho(etTamanhoRoupas.getText().toString());
        }

        return c;
    }

    private void preencherRoupas(ComprasRoupas c) {
        etIDCompraRoupas.setText(String.valueOf(c.getIDCompra()));
        etValorRoupas.setText(String.valueOf(c.getValor()));
        etQuantidadeRoupas.setText(String.valueOf(c.getQuantidade()));
        etTipoRoupas.setText(c.getTipoRoupa());
        etTamanhoRoupas.setText(c.getTamanho());
    }
}

package com.fatec.zl.amos.trabalhosemestral;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.fatec.zl.amos.trabalhosemestral.controller.RendaController;
import com.fatec.zl.amos.trabalhosemestral.model.Renda;
import com.fatec.zl.amos.trabalhosemestral.model.RendaInvestimento;
import com.fatec.zl.amos.trabalhosemestral.persistence.RendaDao;
import java.sql.SQLException;
import java.util.List;

public class Rendas extends Fragment {
    private View view;
    private EditText etFonteRendas, etValorRendas, etIDRendas;
    private TextView tvResRendas;
    private CheckBox cbInvestimento;
    private Button btnCadastrarRendas, btnListarRendas, btnExcluirRendas, btnConsultarRendas, btnAtualizarRendas;
    private RendaController rendaController;

    public Rendas() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rendas, container, false);

        etIDRendas = view.findViewById(R.id.etIDRendas);
        etValorRendas = view.findViewById(R.id.etValorRendas);
        etFonteRendas = view.findViewById(R.id.etFonteRendas);

        btnCadastrarRendas = view.findViewById(R.id.btnCadastrarRendas);
        btnAtualizarRendas = view.findViewById(R.id.btnAtualizarRendas);
        btnExcluirRendas = view.findViewById(R.id.btnExcluirRendas);
        btnListarRendas = view.findViewById(R.id.btnListarRendas);
        btnConsultarRendas = view.findViewById(R.id.btnConsultarRendas);

        cbInvestimento = view.findViewById(R.id.cbInvestimento);
        tvResRendas = view.findViewById(R.id.tvResRendas);
        tvResRendas.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResRendas.setMovementMethod(new ScrollingMovementMethod());

        rendaController = new RendaController(new RendaDao(view.getContext()));

        btnCadastrarRendas.setOnClickListener(op -> acaoCadastrar());
        btnAtualizarRendas.setOnClickListener(op -> acaoAtualizar());
        btnExcluirRendas.setOnClickListener(op -> acaoExcluir());
        btnListarRendas.setOnClickListener(op -> acaoListar());
        btnConsultarRendas.setOnClickListener(op -> acaoConsultar());

        return view;
    }

    private void acaoCadastrar() {
        Renda renda;
        if (cbInvestimento.isChecked()) {
            renda = new RendaInvestimento();
        } else {
            renda = new Renda();
        }

        preencherRenda(renda);

        try {
            rendaController.inserir(renda);
            Toast.makeText(view.getContext(), "Renda cadastrada com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoAtualizar() {
        Renda renda = montarRenda();
        try {
            rendaController.modificar(renda);
            Toast.makeText(view.getContext(), "Renda atualizada com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoExcluir() {
        Renda renda = montarRenda();
        try {
            rendaController.deletar(renda);
            Toast.makeText(view.getContext(), "Renda excluída com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoConsultar() {
        String idText = etIDRendas.getText().toString();
        if (idText.isEmpty()) {
            Toast.makeText(view.getContext(), "Por favor, insira um ID", Toast.LENGTH_LONG).show();
            return;
        }

        Renda renda = montarRenda();
        try {
            Renda consulta = rendaController.buscar(renda);
            if (consulta != null) {
                preencherCampos(consulta);
            } else {
                Toast.makeText(view.getContext(), "Renda não encontrada", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Renda> listaRendas = rendaController.listar();
            StringBuilder buffer = new StringBuilder();
            for (Renda renda : listaRendas) {
                buffer.append(renda.toString()).append("\n");
            }
            tvResRendas.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void preencherRenda(Renda renda) {
        if (!etIDRendas.getText().toString().isEmpty()) {
            renda.setIDRenda(Integer.parseInt(etIDRendas.getText().toString()));
        }
        if (!etValorRendas.getText().toString().isEmpty()) {
            renda.setValorRenda(Float.parseFloat(etValorRendas.getText().toString()));
        }
        renda.setFonteRenda(etFonteRendas.getText().toString());
        renda.setRendaInvestida(cbInvestimento.isChecked());
    }

    private Renda montarRenda() {
        Renda renda = new Renda();

        if (!etIDRendas.getText().toString().isEmpty()) {
            renda.setIDRenda(Integer.parseInt(etIDRendas.getText().toString()));
        }
        if (!etValorRendas.getText().toString().isEmpty()) {
            renda.setValorRenda(Float.parseFloat(etValorRendas.getText().toString()));
        }
        renda.setFonteRenda(etFonteRendas.getText().toString());
        renda.setRendaInvestida(cbInvestimento.isChecked());

        return renda;
    }

    private void limparCampos() {
        etIDRendas.setText("");
        etValorRendas.setText("");
        etFonteRendas.setText("");
        cbInvestimento.setChecked(false);
    }

    private void preencherCampos(Renda renda) {
        etIDRendas.setText(String.valueOf(renda.getIDRenda()));
        etValorRendas.setText(String.valueOf(renda.getValorRenda()));
        etFonteRendas.setText(renda.getFonteRenda());
        cbInvestimento.setChecked(renda.isRendaInvestida());
    }
}

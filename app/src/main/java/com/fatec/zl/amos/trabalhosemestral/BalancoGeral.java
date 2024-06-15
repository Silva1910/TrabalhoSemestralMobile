package com.fatec.zl.amos.trabalhosemestral;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.zl.amos.trabalhosemestral.controller.ComprasMercadoController;
import com.fatec.zl.amos.trabalhosemestral.controller.ComprasOutrosController;
import com.fatec.zl.amos.trabalhosemestral.controller.ComprasRoupasController;
import com.fatec.zl.amos.trabalhosemestral.controller.RendaController;
import com.fatec.zl.amos.trabalhosemestral.model.ComprasMercado;
import com.fatec.zl.amos.trabalhosemestral.model.ComprasOutros;
import com.fatec.zl.amos.trabalhosemestral.model.ComprasRoupas;
import com.fatec.zl.amos.trabalhosemestral.model.Renda;
import com.fatec.zl.amos.trabalhosemestral.persistence.ComprasMercadoDao;
import com.fatec.zl.amos.trabalhosemestral.persistence.ComprasOutrasDao;
import com.fatec.zl.amos.trabalhosemestral.persistence.ComprasRoupasDao;
import com.fatec.zl.amos.trabalhosemestral.persistence.RendaDao;
import java.sql.SQLException;
import java.util.List;

public class BalancoGeral extends Fragment {

    private View view;
    private TextView tvResBalanco;
    private Button btnGerar;
    private RendaController rendaController;
    private ComprasMercadoController mcont;
    private ComprasOutrosController ocont;
    private ComprasRoupasController rcont;

    public BalancoGeral() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_balanco_geral, container, false);

        rendaController = new RendaController(new RendaDao(view.getContext()));
        mcont = new ComprasMercadoController(new ComprasMercadoDao(view.getContext()));
        ocont = new ComprasOutrosController(new ComprasOutrasDao(view.getContext()));
        rcont = new ComprasRoupasController(new ComprasRoupasDao(view.getContext()));

        btnGerar = view.findViewById(R.id.btnGerar);
        tvResBalanco = view.findViewById(R.id.tvResBalanco);
        tvResBalanco.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tvResBalanco.setMovementMethod(new ScrollingMovementMethod());

        btnGerar.setOnClickListener(v -> gerarBalanco());

        return view;
    }

    private void gerarBalanco() {
        try {
            StringBuilder buffer = new StringBuilder();

            List<ComprasOutros> comprasOutros = ocont.listar();
            for (ComprasOutros compra : comprasOutros) {
                buffer.append(compra.toString()).append("\n");
            }

            List<ComprasMercado> comprasMercado = mcont.listar();
            for (ComprasMercado compra : comprasMercado) {
                buffer.append(compra.toString()).append("\n");
            }

            List<ComprasRoupas> comprasRoupas = rcont.listar();
            for (ComprasRoupas compra : comprasRoupas) {
                buffer.append(compra.toString()).append("\n");
            }

            List<Renda> rend = rendaController.listar();
            for (Renda renda : rend) {
                buffer.append(renda.toString()).append("\n");
            }

            tvResBalanco.setText(buffer.toString());

            List<Renda> rendas = rendaController.listar();
            float totalGastos = calcularTotalGastos(comprasMercado, comprasOutros, comprasRoupas);
            float totalRendimentos = calcularTotalRendimentos(rendas);
            float saldoFinal = totalRendimentos - totalGastos;
            String mensagem = "Saldo Final: " + saldoFinal + "\n\nTotal Rendimentos: " + totalRendimentos + "\nTotal Gastos: " + totalGastos;
            tvResBalanco.append("\n\n" + mensagem);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private float calcularTotalGastos(List<ComprasMercado> comprasMercado, List<ComprasOutros> comprasOutros, List<ComprasRoupas> comprasRoupas) {
        float totalGastos = 0;

        for (ComprasMercado compra : comprasMercado) {
            totalGastos += compra.getValor();
        }

        for (ComprasOutros compra : comprasOutros) {
            totalGastos += compra.getValor();
        }

        for (ComprasRoupas compra : comprasRoupas) {
            totalGastos += compra.getValor();
        }

        return totalGastos;
    }

    private float calcularTotalRendimentos(List<Renda> rendas) {
        float totalRendimentos = 0;

        for (Renda renda : rendas) {
            totalRendimentos += renda.getValorRenda();
        }

        return totalRendimentos;
    }
}

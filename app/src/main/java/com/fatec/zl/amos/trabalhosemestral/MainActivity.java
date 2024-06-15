package com.fatec.zl.amos.trabalhosemestral;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            carregarFragment(bundle);}




        }

    private void carregarFragment(Bundle bundle) {
        String tipo = bundle.getString("tipo");
        if (tipo.equals("ComprasOutros")) {
            fragment = new comprasOutros();

        } else if (tipo.equals("ComprasMercado")) {
            fragment = new comprasMercado();

        }else if (tipo.equals("ComprasRoupas")){
            fragment = new comprasRoupas();
        }
        else if (tipo.equals("Renda")){
            fragment = new Rendas();


        }else if (tipo.equals("BalancoGeral")){
            fragment = new BalancoGeral();


        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);


        if (id == R.id.item_comprasOutros) {
            bundle.putString("tipo", "ComprasOutros");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }

        if (id == R.id.Item_comprasMercado) {
            bundle.putString("tipo", "ComprasMercado");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }

        if (id == R.id.item_comprasRoupas) {
            bundle.putString("tipo", "ComprasRoupas");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }

        if (id == R.id.item_renda) {
            bundle.putString("tipo", "Renda");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }
        if (id == R.id.item_BalancoGeral) {
            bundle.putString("tipo", "BalancoGeral");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


}
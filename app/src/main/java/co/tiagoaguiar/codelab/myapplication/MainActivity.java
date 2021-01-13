package co.tiagoaguiar.codelab.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private RecyclerView rvMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		rvMain = findViewById(R.id.main_rv);

		List<MainItem> mainItems = new ArrayList<>();
		mainItems.add(new MainItem(1,R.drawable.ic_baseline_wb_sunny_24, R.string.label_imc, Color.LTGRAY));
		mainItems.add(new MainItem(2,R.drawable.ic_baseline_visibility_24, R.string.tmb, Color.GRAY));

		//1 > Definir o comportamento de exibição do layout da recyclerview
		// mosaic
		// grid
		// linear (horizontal ou vertical)
		rvMain.setLayoutManager(new GridLayoutManager(this, 2));
		MainAdapter adapter = new MainAdapter(mainItems);
		adapter.setListener(id -> {
			switch (id){
				case 1:
					startActivity(new Intent(MainActivity.this, ImcActivity.class));
					break;
				case 2:
					startActivity(new Intent(MainActivity.this, TmbActivity.class));
					break;
			}
		});
		rvMain.setAdapter(adapter);

	}
	private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
		private List<MainItem> mainItems;
		private	OnItemClickListener listener;

		public MainAdapter(List<MainItem> mainItems){
			this.mainItems = mainItems;
		}

		public void setListener(OnItemClickListener listener) {
			this.listener = listener;
		}

		@NonNull
		@Override
		//Esse metodo espera um retorno da celula especifica que foi definida abaixo (MainViewHolder)
		public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			// Espera a View do Layout da celula especifica
			return new  MainViewHolder(getLayoutInflater().inflate(R.layout.main_item,parent,false));
		}

		@Override
		public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
			MainItem mainItemCurrent = mainItems.get(position);
			holder.bind(mainItemCurrent);
		}

		@Override
		//Quantidade de itens da celula que vai precisar
		public int getItemCount() {
			return mainItems.size();
		}
		//Entenda como sendo a VIEW DA CELULA que está dentro do RecyclerView
		private class MainViewHolder extends RecyclerView.ViewHolder{

			public MainViewHolder(@NonNull View itemView) {
				super(itemView);
			}

			public void bind(MainItem item){
				TextView txtName = itemView.findViewById(R.id.item_txt_name);
				ImageView imgIcon = itemView.findViewById(R.id.item_img_icon);
				LinearLayout btnImc = (LinearLayout) itemView.findViewById(R.id.btn_imc);

				btnImc.setOnClickListener(view -> {
					listener.onClick(item.getId());

				});

				txtName.setText(item.getTextStringId());
				imgIcon.setImageResource(item.getDrawableId());
				btnImc.setBackgroundColor(item.getColor());
			}
		}
	}

}
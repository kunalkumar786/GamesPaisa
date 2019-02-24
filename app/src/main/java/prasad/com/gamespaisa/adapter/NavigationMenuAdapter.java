package prasad.com.gamespaisa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.model.NavigationMenuModel;

public class NavigationMenuAdapter  extends RecyclerView.Adapter<NavigationMenuAdapter.MyViewHolder> {
private List<String> navigationList;
    Context context;
    MainActivity mainActivity;
    public class MyViewHolder extends RecyclerView.ViewHolder  {
RelativeLayout rl_option_name;
TextView tv_subcat_add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_subcat_add=(TextView)itemView.findViewById(R.id.tv_subcat_add);
            rl_option_name=(RelativeLayout)itemView.findViewById(R.id.rl_option_name);
            }


    }
public NavigationMenuAdapter(List<String> navigationList, Context context, MainActivity mainActivity){
        this.navigationList=navigationList;
        this.context=context;
        this.mainActivity=mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.navigation_menu_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
     final String navigationMenuModel=navigationList.get(i);
     /*if(navigationMenuModel.equals("")){
         myViewHolder.tv_subcat_add.setVisibility(View.INVISIBLE);
     }else {
         myViewHolder.tv_subcat_add.setText(navigationMenuModel);
     }*/
       myViewHolder.tv_subcat_add.setText(navigationMenuModel);
       myViewHolder.rl_option_name.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mainActivity.customNavigationSelection(navigationMenuModel);
        }
    });
    }

    @Override
    public int getItemCount() {
        return navigationList.size();
    }
}

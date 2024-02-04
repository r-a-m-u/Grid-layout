package com.social.quizme;

import static android.provider.LiveFolders.INTENT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.social.quizme.Adapter.categoryAdapter;
import com.social.quizme.databinding.FragmentHomeBinding;
import com.social.quizme.model.categoryModel;

import java.util.ArrayList;


public class homeFragment extends Fragment {


    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    FragmentHomeBinding binding;
    FirebaseFirestore database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);



        database = FirebaseFirestore.getInstance();


        final ArrayList<categoryModel> categories = new ArrayList<>();
        final categoryAdapter adapter = new categoryAdapter(getContext(), categories);
      database.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
              categories.clear();
              for (DocumentSnapshot snapshot:value.getDocuments()){
                  categoryModel model=snapshot.toObject(categoryModel.class);
                  model.setCategoryId(snapshot.getId());
                  categories.add(model);
              }
              adapter.notifyDataSetChanged();

          }
      });
        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.categoryList.setAdapter(adapter);

//        LinearLayoutManager  layoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
//        binding.categoryList.setLayoutManager(layoutManager);



        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"CLICK OUT OF THE COOL APPLICATION");
                intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.social.quizme");
                //different option show
                startActivity(Intent.createChooser(intent,"Share via"));

            }
        });

        binding.spinWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity((new Intent(getContext(),SpinnerActivity.class)));
            }
        });



        // Inflate the layout for this fragment
        return binding.getRoot();





}

    }
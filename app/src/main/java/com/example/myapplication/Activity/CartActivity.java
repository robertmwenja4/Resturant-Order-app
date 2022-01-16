package com.example.myapplication.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.CartListAdapter;
import com.example.myapplication.Domain.LipaNaMpesa;
import com.example.myapplication.Domain.MpesaModel;
import com.example.myapplication.Domain.MpesaRequestResponse;
import com.example.myapplication.Domain.SaveOrder;
import com.example.myapplication.Helper.ManagementCart;
import com.example.myapplication.Interface.ChangeNumberItemsListener;
import com.example.myapplication.Interface.MpesaInterface;
import com.example.myapplication.Profile;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt,mcheckout;
    private double tax;
    private ScrollView scrollView;
    DatabaseReference dref,dref1;
    String phoneNumber,uid;
    FirebaseAuth fAuth;
    FirebaseUser user1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managementCart = new ManagementCart(this);
        dref = FirebaseDatabase.getInstance().getReference("users");
        dref1 = FirebaseDatabase.getInstance().getReference().child("OrderDetails");
        fAuth = FirebaseAuth.getInstance();
        user1 = fAuth.getCurrentUser();
        uid = user1.getUid();
        mcheckout = findViewById(R.id.checkout);
        mcheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mpesa mpesa = new Mpesa();
                mpesa.execute();
                Checkout();
            }
        });

        initView();
        initList();
        bottomNavigation();
        calculateCard();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNumber = snapshot.child(uid).child("phone").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void Checkout(){

        double percentTax = 0.02;  //you can change this item for tax price
        double delivery = 0;     //you can change this item you need price for delivery

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;
        for (int i = 0; i<managementCart.getListCart().size();i++){
            String title = managementCart.getListCart().get(i).getTitle();
            Toast.makeText(this, "Title is: "+title, Toast.LENGTH_SHORT).show();
            String time = String.valueOf(System.currentTimeMillis());
            String totalcost = String.valueOf(total);
            LocalDate today = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                today = LocalDate.now();
            }
            String Leo = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Leo = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            SaveOrder OrderDetail = new SaveOrder(title,totalcost,Leo,time);
            String key = dref1.push().getKey();
            dref1.child(user1.getUid()).child(key).setValue(OrderDetail);
        }


    }

    private void bottomNavigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout profBtn = findViewById(R.id.profilebtn);


        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));

            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, CartActivity.class));

            }
        });
//        profBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(CartActivity.this, Profile.class));
//            }
//        });
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCard() {
        double percentTax = 0.02;  //you can change this item for tax price
        double delivery = 0;     //you can change this item you need price for delivery

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;


        totalFeeTxt.setText("Ksh" + itemTotal);
        taxTxt.setText("Ksh" + tax);
        deliveryTxt.setText("Ksh" + delivery);
        totalTxt.setText("Ksh" + total);
    }

    private void initView() {
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        recyclerViewList = findViewById(R.id.view);
        scrollView = findViewById(R.id.scrollView);
        emptyTxt = findViewById(R.id.emptyTxt);
    }

    public class Mpesa extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            double percentTax = 0.02;  //you can change this item for tax price
            double delivery = 0;     //you can change this item you need price for delivery

            tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;
            double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
            double itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://sandbox.safaricom.co.ke/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();
            //Instance of Interface
            MpesaInterface mpesaInterface = retrofit.create(MpesaInterface.class);
            String basicToken = "Basic WFhIYlV2Z1BZYWQ4dlh2ODV5WkEyS21HZEN2OUUyVEM6elo0bVZSakNqV285aGc4Sg==";
            Call<MpesaModel> call = mpesaInterface.getData(basicToken);
            call.enqueue(new Callback<MpesaModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<MpesaModel> call, Response<MpesaModel> response) {
                    if (response.code() != 200) {
                        Toast.makeText(CartActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                    String ph = "254"+phoneNumber;
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String businessCode = "174379";
                    String amt = "1";
                    String code = String.valueOf(businessCode);
                    String passKey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
                    SimpleDateFormat df1 = new SimpleDateFormat("YYYYMMddhhmmss");
                    String timme = df1.format(timestamp);
                    String combine = code+passKey+timme;
                    String transact = "CustomerPayBillOnline";
                    String test = "Pay for Food to Reserve";
                    int an = (int) total;
                    String tt = String.valueOf(an);
                    String url = "https://02be-102-167-195-189.ngrok.io/api/mpesa/callback";
                    String bas64 = Base64.getEncoder().encodeToString(combine.getBytes()).toString();
                    Toast.makeText(CartActivity.this, "The cost is:"+tt, Toast.LENGTH_SHORT).show();
                    //Lipa na mpesa body
                    LipaNaMpesa lipaNaMpesa = new LipaNaMpesa(
                            code,
                            bas64,
                            timme,
                            transact,
                            tt,
                            ph,
                            code,
                            ph,
                            url,
                            test,
                            test

                    );

                    Call<MpesaRequestResponse> call2 = mpesaInterface.getRequest("Bearer " +response.body().getAccess_token(), lipaNaMpesa);

                    call2.enqueue(new Callback<MpesaRequestResponse>() {
                        @Override
                        public void onResponse(Call<MpesaRequestResponse> call, Response<MpesaRequestResponse> response) {
                            if (response.code()!= 200){
                                Toast.makeText(CartActivity.this, "Not Working", Toast.LENGTH_SHORT).show();
                            }
                            //Here Callback will happen eg if Mpesa code is retrieved the generate the Authentication code and send to user email
                            //Toast.makeText(CartActivity.this, response.body().getResponseCode(), Toast.LENGTH_SHORT).show();

                            // https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest
                        }

                        @Override
                        public void onFailure(Call<MpesaRequestResponse> call, Throwable t) {

                        }
                    });

                }

                @Override
                public void onFailure(Call<MpesaModel> call, Throwable t) {


                }
            });

            return null;
        }

    }

}
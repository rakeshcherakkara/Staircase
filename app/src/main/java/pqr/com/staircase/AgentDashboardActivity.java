package pqr.com.staircase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AgentDashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    FragmentTransaction fragmentTransaction;
    TextView agentName,agentJoinDate,totalSubAgents,agentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_dashboard);

        //setting toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setting drawer layout

        drawerLayout = findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(AgentDashboardActivity.this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //Navigation View

        navigationView = findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        agentName = header.findViewById(R.id.agentName);
        agentJoinDate = header.findViewById(R.id.agentJoinDate);
        totalSubAgents = header.findViewById(R.id.totalSubAgents);
        agentBalance = header.findViewById(R.id.agentBalance);


        agentName.setText(AgentData.getInstance().getAgent_name()+"("+AgentData.getInstance().getAgent_code()+")");
        agentJoinDate.setText(AgentData.getInstance().getJoin_date());
        totalSubAgents.setText(AgentData.getInstance().getTotal_subagents());
        agentBalance.setText(AgentData.getInstance().getBalance());


        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new AgentDashboard1Fragment());
        fragmentTransaction.commit();

        getSupportActionBar().setTitle(getResources().getString(R.string.agent_dashboard));

        //calling products


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {

                    case R.id.dashboard_id:

                        Intent dash = new Intent(AgentDashboardActivity.this,AgentDashboardActivity.class);
                        startActivity(dash);

                        break;

                    case R.id.agent_sale:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new AgentSaleFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.agent_sale));
                        break;

                    case R.id.sale_report_id:
                        Toast.makeText(AgentDashboardActivity.this,"Sale Report",Toast.LENGTH_LONG).show();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new AgentSaleReportFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.agent_sale_report));
                        break;

                    case R.id.amountwise_report_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new AgentAmountwiseReportFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.amountwise_report));
                        break;

                    case R.id.winners_report_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new AgentWinnersReportFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.winners_report));
                        break;

                    case R.id.result_report_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new ResultReportFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.result_report));
                        break;
                    case R.id.trail_balance_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new TrailBalanceFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.trail_balance));
                        break;

                    case R.id.logout_id:

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AgentDashboardActivity.this);
                        builder1.setTitle(getResources().getString(R.string.logout));
                        builder1.setMessage("Do you really want to Logout?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent serv = new Intent(getApplicationContext(),MyService.class);
                                        getApplicationContext().stopService(serv);
                                        Log.d("Sevice Stopped","Service Stopped....");
                                        Intent home = new Intent(AgentDashboardActivity.this,LoginActivity.class);
                                        startActivity(home);
                                        finish();
                                    }
                                });
                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                        break;



                }



                return true;
            }
        });


    }


    public void loggingout()
    {
        Intent home = new Intent(AgentDashboardActivity.this,LoginActivity.class);
        startActivity(home);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.change_password:
                Toast.makeText(this,getResources().getString(R.string.change_password),Toast.LENGTH_LONG).show();

//                fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//                fragmentTransaction.replace(R.id.main_container,new AgentChangePassword());
//
//                fragmentTransaction.commit();
//
//                drawerLayout.closeDrawers();

                break;
            case R.id.logout:
//                Toast.makeText(this,"Logout",Toast.LENGTH_LONG).show();


                AlertDialog.Builder builder1 = new AlertDialog.Builder(AgentDashboardActivity.this);
                builder1.setTitle(getResources().getString(R.string.logout));
                builder1.setMessage("Do you really want to Logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent serv = new Intent(getApplicationContext(),MyService.class);
                                getApplicationContext().stopService(serv);
                                Intent home = new Intent(AgentDashboardActivity.this,LoginActivity.class);
                                startActivity(home);
                                finish();
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


                break;
        }
        return true;
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}

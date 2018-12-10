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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SubagentDashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    FragmentTransaction fragmentTransaction;
    TextView agentName,agentJoinDate,totalSubAgents,agentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subagent_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(SubagentDashboardActivity.this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView = findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);

        agentName = header.findViewById(R.id.agentName);
        agentJoinDate = header.findViewById(R.id.agentJoinDate);
        totalSubAgents = header.findViewById(R.id.totalSubAgents);
        totalSubAgents.setVisibility(View.INVISIBLE);
        agentBalance = header.findViewById(R.id.agentBalance);

        agentName.setText(SubagentData.getInstance().getSubagent_name()+ "("+SubagentData.getInstance().getSubagent_code()+")");
        agentJoinDate.setText(SubagentData.getInstance().getJoin_date());
        agentBalance.setText(SubagentData.getInstance().getBalance());

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.submain_container,new SubagentDashboard1());
        fragmentTransaction.commit();

        getSupportActionBar().setTitle("Subagent Dashboard");


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {

                    case R.id.dashboard_id:

                        Intent dash = new Intent(SubagentDashboardActivity.this,SubagentDashboardActivity.class);
                        startActivity(dash);

                        break;

                    case R.id.subagent_sale_id:
                        //Toast.makeText(SubagentDashboardActivity.this,"Subagent Sale",Toast.LENGTH_LONG).show();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.submain_container,new SubagentSaleFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.subagent_sale));

                        break;
                    case R.id.subagent_salereport_id:

                        //Toast.makeText(SubagentDashboardActivity.this,"Subagent Sale Report",Toast.LENGTH_LONG).show();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.submain_container,new SubagentSaleReportFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.sale_report));

                        break;

                    case R.id.subagent_amountwise_id:

                        //Toast.makeText(SubagentDashboardActivity.this,"Subagent Amountwise Report",Toast.LENGTH_LONG).show();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.submain_container,new SubagentAmountwiseReportFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.amountwise_report));

                        break;

                    case R.id.subagent_winners_report_id:

                        //Toast.makeText(SubagentDashboardActivity.this,"Subagent Winners Report",Toast.LENGTH_LONG).show();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.submain_container,new SubagentWinnersReportFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.winners_report));

                        break;

                    case R.id.result_report_id:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.submain_container,new ResultReportFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setTitle(getResources().getString(R.string.result_report));

                        break;

                    case R.id.logout_id:

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SubagentDashboardActivity.this);
                        builder1.setTitle(getResources().getString(R.string.logout));
                        builder1.setMessage("Do you really want to Logout?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent home = new Intent(SubagentDashboardActivity.this,LoginActivity.class);
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


                AlertDialog.Builder builder1 = new AlertDialog.Builder(SubagentDashboardActivity.this);
                builder1.setTitle(getResources().getString(R.string.logout));
                builder1.setMessage("Do you really want to Logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent home = new Intent(SubagentDashboardActivity.this,LoginActivity.class);
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

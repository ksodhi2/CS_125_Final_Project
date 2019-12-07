package com.example.cs125finalproject.ui.gened;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cs125finalproject.MySingleton;
import com.example.cs125finalproject.R;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONObject;

public class GenedFragment extends Fragment {

    private String ACP;
    private String CS;
    private String HUM;
    private String NAT;
    private String QR;
    private String SBS;
    private boolean westernChecked;
    private boolean nonwesternChecked;
    private boolean minorityChecked;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_gened, container, false);
        ACP = "N/A";
        CS = "N/A";
        HUM = "N/A";
        NAT = "N/A";
        QR = "N/A";
        SBS = "N/A";
        final Chip advanced = root.findViewById(R.id.ACP);
        final Chip western = root.findViewById(R.id.western);
        final Chip nonwestern = root.findViewById(R.id.nonwestern);
        final Chip minority = root.findViewById(R.id.minority);
        final Chip humanities = root.findViewById(R.id.humanities);
        final Chip science = root.findViewById(R.id.science);
        final Chip quant = root.findViewById(R.id.quantitative);
        final Chip social = root.findViewById(R.id.social);
        westernChecked = false;
        nonwesternChecked = false;
        minorityChecked = false;
        advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (advanced.isChecked()) {
                    ACP = "ACP";
                } else {
                    ACP = "N/A";
                }
                findCourses(root);
            }
        });
        western.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (western.isChecked()) {
                    if (nonwesternChecked || minorityChecked) {
                        westernChecked = true;
                        findCourses(root);
                        return;
                    }
                    westernChecked = true;
                    CS = "WCC";
                } else if (!nonwesternChecked && !minorityChecked) {
                    westernChecked = false;
                    CS = "N/A";
                } else if (minorityChecked) {
                    westernChecked = false;
                    CS = "US";
                } else if (nonwesternChecked) {
                    westernChecked = false;
                    CS = "NW";
                }
                findCourses(root);
            }
        });
        nonwestern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nonwestern.isChecked()) {
                    if (westernChecked || minorityChecked) {
                        nonwesternChecked = true;
                        findCourses(root);
                        return;
                    }
                    nonwesternChecked = true;
                    CS = "NW";
                } else if (!westernChecked && !minorityChecked) {
                    nonwesternChecked = false;
                    CS = "N/A";
                } else if (minorityChecked) {
                    nonwesternChecked = false;
                    CS = "US";
                } else if (westernChecked) {
                    nonwesternChecked = false;
                    CS = "WCC";
                }
                findCourses(root);
            }
        });
        minority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (minority.isChecked()) {
                    if (westernChecked || nonwesternChecked) {
                        minorityChecked = true;
                        findCourses(root);
                        return;
                    }
                    minorityChecked = true;
                    CS = "US";
                } else if (!westernChecked && !nonwesternChecked) {
                    minorityChecked = false;
                    CS = "N/A";
                } else if (westernChecked) {
                    minorityChecked = false;
                    CS = "WCC";
                } else if (nonwesternChecked) {
                    minorityChecked = false;
                    CS = "NW";
                }
                findCourses(root);
            }
        });
        humanities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (humanities.isChecked()) {
                    HUM = "HP";
                } else {
                    HUM = "N/A";
                }
                findCourses(root);
            }
        });
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (science.isChecked()) {
                    NAT = "LS";
                } else {
                    NAT = "N/A";
                }
                findCourses(root);
            }
        });
        quant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quant.isChecked()) {
                    QR = "QR1";
                } else {
                    QR = "N/A";
                }
                findCourses(root);
            }
        });
        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (social.isChecked()) {
                    SBS = "BS";
                } else {
                    SBS = "N/A";
                }
                findCourses(root);
            }
        });
        findCourses(root);
        return root;
    }


    private void findCourses(View root) {
        final TextView genEdText = root.findViewById(R.id.genEdText);
        genEdText.setMovementMethod(new ScrollingMovementMethod());
        if (ACP.equals("N/A") && CS.equals("N/A") && HUM.equals("N/A") && NAT.equals("N/A") && QR.equals("N/A") && SBS.equals("N/A")) {
            genEdText.setText("Choose Gen Ed Categories");
            return;
        }
        if ((westernChecked && nonwesternChecked) || (nonwesternChecked && minorityChecked) || (westernChecked && minorityChecked)) {
            genEdText.setText("");
            return;
        }
        JSONObject param = new JSONObject();
        try {
            param.put("ACP", ACP); // ACP for "Advanced Composition"; values ACP or N/A
            param.put("CS", CS); // CS for "Cultural Studies"; values are NW for "Non-Western Cultures", WCC for "Western/Comparative Cultures", US for "US Minority Cultures", or N/A
            param.put("HUM", HUM); // HUM for "Humanities & the Arts"; values are HP or N/A
            param.put("NAT", NAT); // NAT for "Natural Sciences & Technology"; value are LS or N/A
            param.put("QR", QR); // QR for "Quantitative Reasoning"; values are QR1 or N/A
            param.put("SBS", SBS); // SBS for "Social & Behavioral Sciences"; values are BS or N/A
        } catch (Exception e) {
            return;
        }
        String url = "http://uiuc.us-east-1.elasticbeanstalk.com/gened";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, param, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String classes = "";
                        try {
                            JSONArray result = response.getJSONArray("result");
                            for (int i = 0; i < result.length(); i++) {
                                classes = classes + result.getJSONObject(i).get("Number").toString() + " -- " + result.getJSONObject(i).get("Name").toString() + "\n"
                                        + "Average GPA: " + result.getJSONObject(i).get("GPA").toString() + ", from a dataset of " +
                                        result.getJSONObject(i).get("Total Students").toString().substring(0, result.getJSONObject(i).get("Total Students").toString().length() - 2) + " students" + "\n" + "\n";
                            }
                        } catch (Exception e) {
                            return;
                        }
                        genEdText.setText(classes);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error please connect to the internet or try again later", Toast.LENGTH_LONG).show();
                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }
}
package com.example.cs125finalproject.ui.gened;

import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

public class GenedFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gened, container, false);
        connect(root);
        return root;
    }
    private void connect(View root) {
        JSONObject param = new JSONObject();
        try {
            param.put("ACP", "ACP"); // ACP for "Advanced Composition"; values ACP or N/A
            param.put("CS", "N/A"); // CS for "Cultural Studies"; values are NW for "Non-Western Cultures", WCC for "Western/Comparative Cultures", US for "US Minority Cultures", or N/A
            param.put("HUM", "N/A"); // HUM for "Humanities & the Arts"; values are HP or N/A
            param.put("NAT", "N/A"); // NAT for "Natural Sciences & Technology"; value are LS or N/A
            param.put("QR", "N/A"); // QR for "Quantitative Reasoning"; values are QR1 or N/A
            param.put("SBS", "N/A"); // SBS for "Social & Behavioral Sciences"; values are BS or N/A
        } catch (Exception e) {
            return;
        }
        final TextView genEdText = root.findViewById(R.id.genEdText);
        String url = "http://uiuc.us-east-1.elasticbeanstalk.com/gened";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, param, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String classes = "";
                        try {
                            JSONArray result = response.getJSONArray("result");
                            for (int i = 0; i < result.length(); i++) {
                                classes = classes + result.getJSONObject(i).get("Number").toString()+ " -- " + result.getJSONObject(i).get("Name").toString() + "\n";
                            }
                        } catch (Exception e) {
                            return;
                        }
                        genEdText.setText(classes);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }
}
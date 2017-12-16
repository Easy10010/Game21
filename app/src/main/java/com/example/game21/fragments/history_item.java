package com.example.game21.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.game21.R;
import com.example.game21.other.pokeCard;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link history_item.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link history_item#newInstance} factory method to
 * create an instance of this fragment.
 */
public class history_item extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] args;
    private OnFragmentInteractionListener mListener;
    private Context mContext;

    public history_item() {

    }
    public void setData(String s[] , Context ctx){
        args = s;
        mContext = ctx;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment history_item.
     */
    // TODO: Rename and change types and number of parameters
    public static history_item newInstance(String param1, String param2) {
        history_item fragment = new history_item();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_history_item, container, false);

        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ((TextView)v.findViewById(R.id.timestamp)).setText(
               dateFormat.format(new Date(
                       Long.parseLong(args[0])
               ))
        );

        ((TextView)v.findViewById(R.id.gamestatus)).setText(
                getResources().getStringArray(R.array.GAME_STATUS)[Integer.parseInt(args[1])]
        );
        TextView tv_addEXP = ((TextView)v.findViewById(R.id.addEXP));
        if (Integer.parseInt(args[6]) < 0){
            tv_addEXP.setText(String.format("- %d exp",-Integer.parseInt(args[6])));
//            tv_addEXP.setTextColor(Color.parseColor("#cc0000"));
        }else{
            tv_addEXP.setText(String.format("+ %s exp",args[6]));
        }

        ((TextView)v.findViewById(R.id.textView9)).setText("BOT\n"+args[2]);
        ((TextView)v.findViewById(R.id.textView10)).setText("GAMER\n"+args[4]);

        String[] bot_s = args[3].split("\\|");
        String[] gamer_s = args[5].split("\\|");
        int maxWidth = dp2px(285) / 5;
        int maxHeight = dp2px(70);

        for (int i = 0;i<bot_s.length;i++){
            ((LinearLayout) v.findViewById(R.id.linearLayout2)).addView(
                    new pokeCard(
                            mContext,
                            bot_s[i].substring(0,bot_s[i].length()-1),
                            Integer.parseInt(bot_s[i].substring(bot_s[i].length()-1,bot_s[i].length())),
                        maxWidth,maxHeight
                    )
            );
        }
        for (int i = 0;i<gamer_s.length;i++){
            ((LinearLayout) v.findViewById(R.id.linearLayout3)).addView(
                    new pokeCard(
                            mContext,
                            gamer_s[i].substring(0,gamer_s[i].length()-1),
                            Integer.parseInt(gamer_s[i].substring(gamer_s[i].length()-1,gamer_s[i].length())),
                            maxWidth,maxHeight
                    )
            );
        }
        return v;
    }
    private int dp2px(float dpValue){
        return (int)(dpValue * (mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

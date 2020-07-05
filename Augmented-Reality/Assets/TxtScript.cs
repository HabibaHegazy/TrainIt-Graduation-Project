using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;


public class TxtScript : MonoBehaviour
{

    private AndroidJavaObject javaClass;
    public TextMeshPro txt;
    //private int x = 0;

    // Start is called before tshe first frame update
    void Start()
    {
        txt = GetComponent<TextMeshPro>();
        javaClass = new AndroidJavaObject("com.example.ipingpong.views.PlayerProfile.SessionTrainingFragment");
        /*if (Application.isMobilePlatform)
        {
            txt.text = javaClass.Call<string>("playedStrokeAR");
        }*/
    }

    // Update is called once per frame
    void Update()
    {
        //txt.SetText("hi");
        javaClass = new AndroidJavaObject("com.example.ipingpong.views.PlayerProfile.SessionTrainingFragment");
        Invoke("callAndorid", 0.5f);
    }

    void callAndorid()
    {
        //string s = x.ToString();
        //txt.SetText(s);
        //x++;
        if (Application.isMobilePlatform)
            txt.text = javaClass.Call<string>("playedStrokeAR");
    }
}

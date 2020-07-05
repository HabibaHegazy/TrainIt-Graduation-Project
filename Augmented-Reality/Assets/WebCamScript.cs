using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class WebCamScript : MonoBehaviour
{

    public GameObject webCameraPlane;

    // Start is called before the first frame update
    void Start()
    {
        if (Application.isMobilePlatform) {
            GameObject cameraParent = new GameObject ("camParent");
            cameraParent.transform.position = this.transform.position;
            this.transform.parent = cameraParent.transform;
            cameraParent.transform.Rotate (Vector3.right, 90);
        }

        Input.gyro.enabled = true;


        WebCamTexture webCameraTexture = new WebCamTexture();
        webCameraPlane.GetComponent<MeshRenderer>().material.mainTexture = webCameraTexture;
        webCameraTexture.Play();


    }

    // Update is called once per frame
    void Update()
    {
        Quaternion cameraRotation = new Quaternion (Input.gyro.attitude.x, Input.gyro.attitude.y, -Input.gyro.attitude.z, -Input.gyro.attitude.w);
        this.transform.localRotation = cameraRotation;

        if (Application.isMobilePlatform){
            if (Input.GetKeyDown(KeyCode.Escape))
                Application.Quit();
        }

        

    }
}

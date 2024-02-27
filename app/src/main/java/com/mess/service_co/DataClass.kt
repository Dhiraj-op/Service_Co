package com.mess.service_co

import android.media.Image
import android.media.Rating
import com.google.android.material.animation.MotionTiming

class DataClass {
    var dataImage:String?=null
    var dataName:String?=null
    var dataContact:String?=null
    var dataServices:String?=null
    var dataAadhar:String?=null
    var dataAdd:String?=null

    constructor(dataImage:String?,dataName:String?,dataContact:String?,dataServices: String?,dataAadhar:String?,dataAdd:String?){
        this.dataImage=dataImage
        this.dataName=dataName
        this.dataContact=dataContact
        this.dataServices=dataServices
        this.dataAadhar=dataAadhar
        this.dataAdd=dataAdd
    }
}
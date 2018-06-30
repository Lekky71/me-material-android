package com.teammusa.mematerial.models.requests

/**
 * Created by HashCode on 10:45 AM 30/06/2018.
 */

data class SignUpRequest(var profile_pic:String, var first_name:String, var last_name:String, var username:String, var phone_number:String,
                         var city:String, var state:String, var password:String){
    constructor() : this("","","","","","","","")
}

data class LoginRequest(var username:String, var password:String){
    constructor() : this("", "")
}

data class CreateMaterialRequest(var title:String, var description:String, var category:String, var image:String, var user_id:String, var materials:ArrayList<String>)

data class NewMessageRequest(var course_id:String, var user_id:String, var body:String)

data class MessageFetchRequest(var chat_id:String)
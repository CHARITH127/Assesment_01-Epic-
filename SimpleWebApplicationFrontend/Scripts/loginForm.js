$("#btn_login_button").click(function () {
    let userName = $("#login_user_name").val();
    let password = $("#login_password").val();

    loginObj = {
        userId:userName,
        userPassword:password,
    }

    $.ajax({
        url:"http://localhost:8080/SimpleWebApplication/login",
        method:"POST",
        data:JSON.stringify(loginObj),
        success:function (res) {
            console.log(res.password)
            if (res.message=="true"){
                $("#loginPage").css("display", "none");
                $("#customerPageContext").css("display", "block");
            }else {
                alert("wrong password");
            }
        }
    })
})

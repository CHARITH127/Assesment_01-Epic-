$("#btn_login_button").click(function () {
    let userName = $("#login_user_name").val();
    let password = $("#login_password").val();

    loginObj = {
        userName:userName,
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
                $("#login_user_name").val("");
                $("#login_password").val("");
            }else {
                swal("Error to Login!", "Incorrect user or password!", "error");
            }
        }
    })
})

/*-----------------loading all customers---------------*/
async function loadAllCustomers() {
    $(".customerTableBody").empty();
    $.ajax({
        url: "http://localhost:8080/SimpleWebApplication/customer?options=getAll",
        method: "GET",
        success: function (resp) {
            for (const cust of resp) {
                let row = `<tr><td>${cust.userID}</td><td>${cust.userName}</td><td>${cust.address}</td><td>${cust.email}</td><td>${cust.telephoneNumber}</td><td>${cust.password}</td></tr>`;
                $(".customerTableBody").append(row)
            }
        }
    });
}

loadAllCustomers();

/*-----------------save customers---------------*/

async function saveCustomer() {
    let userId = $("#userId").val();
    let userName = $("#userName").val();
    let address = $("#address").val();
    let email = $("#email").val();
    let telephoneNumber = $("#telephoneNumber").val();
    let password = $("#password").val();

    customer = {
        userID: userId,
        userName: userName,
        address: address,
        email: email,
        telephoneNumber: telephoneNumber,
        password: password
    }

    $.ajax({
        url: "http://localhost:8080/SimpleWebApplication/customer",
        method: "POST",
        data: JSON.stringify(customer),
        success: function (resp) {
            if (resp.message == "Successfully Added") {
                alert("Successfully add the Customer");
                loadAllCustomers();
                clearAll();
                $('#btnCustomerSave').prop('disabled', true);
                $('#btnUpdateCustomer').prop('disabled', true);
            } else {
                alert("Can't add the Customer");
            }
        }
    })
}

$("#btnSearchCustomer").click(function () {
    let userId = $("#userId").val();
    $.ajax({
        url:"http://localhost:8080/SimpleWebApplication/customer?options=search&userId="+userId,
        method:"GET",
        success:function (res) {
            if (res.message == "null") {
                alert("Can't find such a customer");
            }else {
                $("#userId").val(res.data.userID);
                $("#userName").val(res.data.userName);
                $("#address").val(res.data.address);
                $("#email").val(res.data.email);
                $("#telephoneNumber").val(res.data.telephoneNumber);
                $("#password").val(res.data.password);
            }
        }
    })
})


$("#btnCustomerSave").click(function () {
    saveCustomer();
})

/*-----------------select a customer when clicked a table row---------------*/
$("#customerTabele").click(function (e) {
    let clicked_userId = $(e.target).closest('tr').find('td').eq(0).html();
    let clicked_userName = $(e.target).closest('tr').find('td').eq(1).html();
    let clicked_userAddress = $(e.target).closest('tr').find('td').eq(2).html();
    let clicked_userEmail = $(e.target).closest('tr').find('td').eq(3).html();
    let clicked_userTelephoneNumber = $(e.target).closest('tr').find('td').eq(4).html();
    let clicked_userPassword = $(e.target).closest('tr').find('td').eq(5).html();

    $("#userId").val(clicked_userId);
    $("#userName").val(clicked_userName);
    $("#address").val(clicked_userAddress);
    $("#email").val(clicked_userEmail);
    $("#telephoneNumber").val(clicked_userTelephoneNumber);
    $("#password").val(clicked_userPassword);
})

/*-----------------update customers---------------*/
$("#btnUpdateCustomer").click(function () {

    let userId = $("#userId").val();
    let userName = $("#userName").val();
    let address = $("#address").val();
    let email = $("#email").val();
    let telephoneNumber = $("#telephoneNumber").val();
    let password = $("#password").val();

    if (userId == null || userName == null || address == null || email == null || telephoneNumber == null || password == null) {
        alert("Please select a customer");
    } else {
        customer = {
            userID: userId,
            userName: userName,
            address: address,
            email: email,
            telephoneNumber: telephoneNumber,
            password: password
        }

        $.ajax({
            url: "http://localhost:8080/SimpleWebApplication/customer",
            method: "PUT",
            data: JSON.stringify(customer),
            success: function (resp) {
                if (resp.message == "Successfully Updated") {
                    alert("Successfully Update the Customer");
                    loadAllCustomers();
                    clearAll();
                    $('#btnCustomerSave').prop('disabled', true);
                    $('#btnUpdateCustomer').prop('disabled', true);
                } else {
                    alert("Can't update the Customer");
                }
            }
        })
    }
})

/*-----------------delete customers---------------*/
$("#btnDeleteCustomer").click(function () {
    let userId = $("#userId").val();

    $.ajax({
        url: "http://localhost:8080/SimpleWebApplication/customer?userId=" + userId,
        method: "DELETE",
        success: function (resp) {
            if (resp.message == "Successfully deleted") {
                alert("Successfully deleted the Customer");
                loadAllCustomers();
                clearAll()
            } else {
                alert("Can't delete the Customer");
            }
        }
    })
})

/*-----------------clearAll---------------*/
function clearAll() {
    $("#userId").val("");
    $("#userName").val("");
    $("#address").val("");
    $("#email").val("");
    $("#telephoneNumber").val("");
    $("#password").val("");
}

/*------------ validation --------------*/

function validation() {
    const userIdRegex = /^(C)[0-9]{1,3}$/;
    const userNameRegex = /^[A-z ]{5,20}$/;
    const userAddress = /^[A-z ]{5,20}$/;
    const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    const telephoneNumberRegex = /^[0-9]{10}$/;
    const passwordRegex = /(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}/;

    $("#userId").keydown(function (e) {
        if (e.key == "Enter") {
            let outUserId = $("#userId").val();
            if (userIdRegex.test(outUserId)) {
                $("#userId").css('border-color', '#04db14');
                $("#userId").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                $("#userId").css('color', '#04db14');
                $("#userName").focus();

                $("#userName").keydown(function (e) {
                    if (e.key == "Enter") {
                        let outUserName = $("#userName").val();
                        if (userNameRegex.test(outUserName)) {
                            $("#userName").css('border-color', '#04db14');
                            $("#userName").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                            $("#userName").css('color', '#04db14');
                            $("#address").focus();

                            $("#address").keydown(function (e) {
                                if (e.key == "Enter") {
                                    let outUserAddress = $("#address").val();
                                    if (userAddress.test(outUserAddress)) {
                                        $("#address").css('border-color', '#04db14');
                                        $("#address").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                        $("#address").css('color', '#04db14');
                                        $("#email").focus();

                                        $("#email").keydown(function (e) {
                                            if (e.key == "Enter") {
                                                let outUserEmail = $("#email").val();
                                                if (emailRegex.test(outUserEmail)) {
                                                    $("#email").css('border-color', '#04db14');
                                                    $("#email").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                    $("#email").css('color', '#04db14');
                                                    $("#telephoneNumber").focus();

                                                    $("#telephoneNumber").keydown(function (e) {
                                                        if (e.key == "Enter") {
                                                            let outUserMobileNum = $("#telephoneNumber").val();
                                                            if (telephoneNumberRegex.test(outUserMobileNum)) {
                                                                $("#telephoneNumber").css('border-color', '#04db14');
                                                                $("#telephoneNumber").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                $("#telephoneNumber").css('color', '#04db14');
                                                                $("#password").focus();

                                                                $("#password").keydown(function (e) {
                                                                    if (e.key == "Enter") {
                                                                        let outPassword = $("#password").val();
                                                                        if (passwordRegex.test(outPassword)) {
                                                                            $("#password").css('border-color', '#04db14');
                                                                            $("#password").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                            $("#password").css('color', '#04db14');
                                                                            $('#btnCustomerSave').prop('disabled', false);
                                                                            $('#btnUpdateCustomer').prop('disabled', false);
                                                                        }else {
                                                                            $("#password").css('border-color', '#ff0202');
                                                                            $("#password").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                            $("#password").css('color', '#ff0202');
                                                                            $('#btnCustomerSave').prop('disabled', true);
                                                                            $('#btnUpdateCustomer').prop('disabled', true);
                                                                        }
                                                                    }
                                                                })

                                                            }else {
                                                                $("#telephoneNumber").css('border-color', '#ff0202');
                                                                $("#telephoneNumber").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                $("#telephoneNumber").css('color', '#ff0202');
                                                                $('#btnCustomerSave').prop('disabled', true);
                                                                $('#btnUpdateCustomer').prop('disabled', true);
                                                            }
                                                        }
                                                    })

                                                }else {
                                                    $("#email").css('border-color', '#ff0202');
                                                    $("#email").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                    $("#email").css('color', '#ff0202');
                                                    $('#btnCustomerSave').prop('disabled', true);
                                                    $('#btnUpdateCustomer').prop('disabled', true);
                                                }
                                            }
                                        })

                                    }else {
                                        $("#address").css('border-color', '#ff0202');
                                        $("#address").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                        $("#address").css('color', '#ff0202');
                                        $('#btnCustomerSave').prop('disabled', true);
                                        $('#btnUpdateCustomer').prop('disabled', true);
                                    }
                                }
                            });

                        } else {
                            $("#userName").css('border-color', '#ff0202');
                            $("#userName").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                            $("#userName").css('color', '#ff0202');
                            $('#btnCustomerSave').prop('disabled', true);
                            $('#btnUpdateCustomer').prop('disabled', true);
                        }
                    }
                });


            } else {
                $("#userId").css('border-color', '#ff0202');
                $("#userId").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                $("#userId").css('color', '#ff0202');
                $('#btnCustomerSave').prop('disabled', true);
                $('#btnUpdateCustomer').prop('disabled', true);
            }
        }
    });


}

validation();

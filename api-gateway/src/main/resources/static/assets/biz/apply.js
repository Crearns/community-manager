var userInfo;

function showUserInfo() {
    userInfo = getUser();
    $("#username").text(userInfo.realName);
}


function apply() {
    const urlParams = new URLSearchParams(window.location.search);
    var page = urlParams.get('page');
    if (page === null) {
        page = 1;
    }
    $.ajax({
        url: "web/worksheet/worksheetInfo",
        type: "get",
        dataType: "json",
        data: {
            id: userInfo.id,
            currentPage: page
        },
        success: function (res) {
            var sum = 0;
            if (res.code === 0) {
                $.each(res.data, function (idx, val) {
                    jsonString = JSON.stringify(val).replace("'", "`");
                    sum++;
                    if (val.state === "申请失败") style = "color: red";
                    else style = "color: green";
                    str = "<tr>\n" +
                        "<td><a onclick='showDetails("+jsonString+")'>"+sum + "." + val.title+"</a></td>\n" +
                        "<td>"+dateFormat(val.create)+"</td>\n" +
                        "<td>"+dateFormat(val.modified)+"</td>\n" +
                        "<td style='"+style+"'>"+val.state+"</td>\n" +
                        "</tr>";

                    $("#applyTable").append(str)
                });
                sum++;
                for (; sum <= 20; sum++) {
                    str = "<tr>\n" +
                        "<td>"+sum+".</td>\n" +
                        "<td></td>\n" +
                        "<td></td>\n" +
                        "<td></td>\n" +
                        "</tr>";

                    $("#applyTable").append(str)
                }
                showPageInfo(res.msg, page)
            }
        }, error: function (err) {
            alert(JSON.stringify(err))
        }
    })
}





function showPageInfo(page, currentPage) {
    pageInt = parseInt(page);
    currentPage = parseInt(currentPage);

    if (currentPage > 1 && currentPage <= pageInt) {
        pre = "<a href='apply.html?page="+(currentPage-1)+"'>上一页</a>"
    } else {
        pre = ""
    }

    if (currentPage >= pageInt || currentPage <= 0) {
        next = ""
    } else {
        next = "<a href='apply.html?page="+(currentPage+1)+"'>下一页</a>"
    }

    str = "<tr>\n" +
        "<td colspan='4' id='page'></td>\n" +
        "</tr>";

    $("#verifyTable").append(str);
    $("#page").append("<a href='apply.html'>首页</a>");
    $("#page").append(pre+" 第"+currentPage+"页，共"+pageInt+"页 "+next);
    $("#page").append("<a href='apply.html?page="+pageInt+"'>尾页</a>");
}


showUserInfo();
apply();
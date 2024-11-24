const input_checkbox_color = document.querySelectorAll(".checkbox_color")
const lable_radio_color = document.querySelectorAll(".lable_radio_color")
const lable_checkbox_color = document.querySelectorAll(".lable_checkbox_color")

const input_checkbox_size = document.querySelectorAll(".checkbox_size")
const lable_radio_size = document.querySelectorAll(".lable_radio_size")
const lable_checkbox_size = document.querySelectorAll(".lable_checkbox_size")

const btn_detail = document.getElementById("btn_detail")
const btn_review = document.getElementById("btn_review")
const view_review = document.getElementById("view_review")
const view_detail = document.getElementById("view_detail")

//radio
for (let i = 0; i < lable_radio_color.length; i++) {
    lable_radio_color[i].addEventListener("click", function() {
        for(let j = 0; j < lable_radio_color.length; j++) {
            lable_radio_color[j].classList.remove("selected_radio_color")
            lable_radio_color[j].classList.add("non_radio_color")
        }
        lable_radio_color[i].classList.add("selected_radio_color")
    });
}

//chekbox
for (let i = 0; i < lable_checkbox_color.length; i++) {
    lable_checkbox_color[i].addEventListener("click", function() {
        if(input_checkbox_color[i].checked == true) {
            lable_checkbox_color[i].classList.remove("selected_checkbox_color")
            lable_checkbox_color[i].classList.add("none_checkbox_color")
        } else {
            lable_checkbox_color[i].classList.add("selected_checkbox_color")
            lable_checkbox_color[i].classList.remove("none_checkbox_color")
        }
    });
}
//sử lý khi chọn size
//radio
for (let i = 0; i < lable_radio_size.length; i++) {
    lable_radio_size[i].addEventListener("click", function() {
        for(let j = 0; j < lable_radio_size.length; j++) {
            lable_radio_size[j].classList.remove("selected_radio_size")
            lable_radio_size[j].classList.add("non_radio_size")
        }
        lable_radio_size[i].classList.add("selected_radio_size")
    });
}
//chekbox
for (let i = 0; i < lable_checkbox_size.length; i++) {
    lable_checkbox_size[i].addEventListener("click", function() {
        if(input_checkbox_size[i].checked == true) {
            lable_checkbox_size[i].classList.remove("selected_checkbox_size")
            lable_checkbox_size[i].classList.add("non_radio_size")
        } else {
            lable_checkbox_size[i].classList.add("selected_checkbox_size")
            lable_checkbox_size[i].classList.remove("non_radio_size")
        }
    });
}

//sử lý khi chọn detail hoặc review

if(btn_detail != null) {
    btn_detail.addEventListener("click", function () {
        btn_review.classList.remove("slected_tab_detail")
        btn_review.classList.add("non_select_tab_detail")
        btn_detail.classList.remove("non_select_tab_detail")
        btn_detail.classList.add("slected_tab_detail")
        view_detail.classList.remove("display_none")
        view_detail.classList.add("display_block")
        view_review.classList.remove("display_block")
        view_review.classList.add("display_none")

    })
}
if(btn_review != null) {
    btn_review.addEventListener("click", function () {
        btn_review.classList.add("slected_tab_detail")
        btn_review.classList.remove("non_select_tab_detail")
        btn_detail.classList.add("non_select_tab_detail")
        btn_detail.classList.remove("slected_tab_detail")
        view_detail.classList.add("display_none")
        view_detail.classList.remove("display_block")
        view_review.classList.add("display_block")
        view_review.classList.remove("display_none")
    })
}


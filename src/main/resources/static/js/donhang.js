const buttons = document.querySelectorAll('.status-container .status-item button');

// Thêm sự kiện click cho từng nút
buttons.forEach(button => {
    button.addEventListener('click', () => {
        // Xóa lớp btn-primary khỏi tất cả các nút
        buttons.forEach(btn => btn.classList.remove('btn-primary', 'btn-outline-secondary'));
        // Thêm lớp btn-primary vào nút được nhấn
        button.classList.add('btn-primary');
        // Các nút khác sẽ có lớp btn-outline-secondary
        buttons.forEach(btn => {
            if (btn !== button) {
                btn.classList.add('btn-outline-secondary');
            }
        });
    });
});

function setActive(menuItem) {
    const menuItems = document.querySelectorAll('.active');
    menuItems.forEach(item => {
        item.classList.remove('active'); // Remove active class from all items
    });
    menuItem.classList.add('active'); // Add active class to clicked item
}


function changeLink(pageNo){
    const pathname = window.location.pathname;
    window.location.href = pathname + "?pageNo="+pageNo;

}


function choXacNhan(){
    window.location.href = "/user/don-hang";
}


function dangXuLy(){
    window.location.href = "/user/dang-xu-ly";
}

function dangGiaoHang(){
    window.location.href = "/user/dang-giao-hang";
}

function hoanThanh(){
    window.location.href = "/user/hoan-thanh";
}

function daHuy(){
    window.location.href = "/user/da-huy";
}

function donHangChiTiet(id){
    window.location.href = "/user/don-hang-chi-tiet-kh?id="+id ;
}


(function () {
    'use strict';
    // Fetch all forms with class 'needs-validation'
    var forms = document.querySelectorAll('.needs-validation');

    // Loop over forms and prevent submission if validation fails
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
                showAlert("error", "Đã có lỗi xảy ra. Vui lòng kiểm tra lại.");
            }
            form.classList.add('was-validated');
        }, false);

        // Add 'change' event listener for all inputs to validate them dynamically
        var inputs = form.querySelectorAll('input');
        Array.prototype.slice.call(inputs).forEach(function (input) {
            input.addEventListener('change', function () {
                if (input.checkValidity()) {
                    input.classList.remove('is-invalid');
                    input.classList.add('is-valid');
                } else {
                    input.classList.remove('is-valid');
                    input.classList.add('is-invalid');
                    showAlert("error", "Dữ liệu không hợp lệ trong " + input.placeholder);
                }
            });
        });
    });

    //thông báo
    function showAlert(type, message) {
        let alertElement;

        if (type === 'success') {
            alertElement = document.getElementById('successAlert');
            alertElement.querySelector('span').textContent = message; // Thiết lập nội dung thông báo thành công
            alertElement.style.display = 'block'; // Hiển thị thông báo thành công
        } else if (type === 'error') {
            alertElement = document.getElementById('alertMessage');
            alertElement.querySelector('#alertBody').textContent = message; // Thiết lập nội dung thông báo lỗi
            alertElement.style.display = 'block'; // Hiển thị thông báo lỗi
        }

        // Tự động ẩn thông báo sau 5 giây
        setTimeout(() => {
            if (alertElement) {
                var bootstrapAlert = new bootstrap.Alert(alertElement);
                bootstrapAlert.close();
            }
        }, 5000);
    }

    document.addEventListener('DOMContentLoaded', function () {
        // Kiểm tra và ẩn thông báo thành công nếu có
        var successAlert = document.getElementById('successAlert');
        if (successAlert && successAlert.style.display !== 'none') {
            setTimeout(function () {
                var bootstrapAlert = new bootstrap.Alert(successAlert);
                bootstrapAlert.close();
            }, 5000);
        }
    });

})();







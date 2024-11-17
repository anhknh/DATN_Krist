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
    window.location.href = "/quan-ly/don-hang";
}


function dangXuLy(){
    window.location.href = "/quan-ly/dang-xu-ly";
}

function dangGiaoHang(){
    window.location.href = "/quan-ly/dang-giao-hang";
}

function hoanThanh(){
    window.location.href = "/quan-ly/hoan-thanh";
}

function donHangChiTiet(id){
    window.location.href = "/quan-ly/don-hang-chi-tiet-kh?id="+id ;
}
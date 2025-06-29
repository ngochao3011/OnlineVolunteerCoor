<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<main>
    <section class="hero-section hero-section-full-height">
        <div class="container-fluid">
            <div class="row">

                <div class="col-lg-12 col-12 p-0">
                    <div id="hero-slide" class="carousel carousel-fade slide" data-bs-ride="carousel">
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <img src="${pageContext.request.contextPath}/src/images/slide/helping-hands-volunteer-support-community-service-graphic.jpg"
                                     class="carousel-image img-fluid" alt="...">

                                <div class="carousel-caption d-flex flex-column justify-content-end">
                                    <h1>be a Kind Heart</h1>

                                    <p>Professional charity theme based on Bootstrap 5.2.2</p>
                                </div>
                            </div>

                            <div class="carousel-item">
                                <img src="${pageContext.request.contextPath}/src/images/slide/volunteer-selecting-organizing-clothes-donations-charity.jpg"
                                     class="carousel-image img-fluid" alt="...">

                                <div class="carousel-caption d-flex flex-column justify-content-end">
                                    <h1>Non-profit</h1>

                                    <p>You can support us to grow more</p>
                                </div>
                            </div>

                            <div class="carousel-item">
                                <img src="${pageContext.request.contextPath}/src/images/slide/medium-shot-people-collecting-donations.jpg"
                                     class="carousel-image img-fluid" alt="...">

                                <div class="carousel-caption d-flex flex-column justify-content-end">
                                    <h1>Humanity</h1>

                                    <p>Please tell your friends about our website</p>
                                </div>
                            </div>
                        </div>

                        <button class="carousel-control-prev" type="button" data-bs-target="#hero-slide"
                                data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>

                        <button class="carousel-control-next" type="button" data-bs-target="#hero-slide"
                                data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>

            </div>
        </div>
    </section>

    <section class="section-padding" id="section_2">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 col-12 text-center mb-4">
                    <h2>Các Hoạt Động Tình Nguyện Nổi Bật</h2>
                </div>

                <c:forEach var="hoatDong" items="${danhSachHoatDong}" begin="0" end="2">
                    <div class="col-lg-4 col-md-6 col-12 mb-4 mb-lg-0">
                        <div class="custom-block-wrap">
                            <c:choose>
                                < c:when test="${isLoggedIn}">
                                <a href="${pageContext.request.contextPath}/activity/details/${hoatDong.maHoatDong}">
                                    <img src="https://mir-s3-cdn-cf.behance.net/projects/404/0967f4197995765.Y3JvcCwxNTM0LDEyMDAsMzAwLDA.png"
                                         class="custom-block-image img-fluid" alt="${hoatDong.tenHoatDong}">
                                </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/sign-in?redirect=/activity/details/${hoatDong.maHoatDong}"
                                       onclick="alert('Vui lòng đăng nhập để xem chi tiết hoạt động!')">
                                        <img src="https://mir-s3-cdn-cf.behance.net/projects/404/0967f4197995765.Y3JvcCwxNTM0LDEyMDAsMzAwLDA.png"
                                             class="custom-block-image img-fluid" alt="${hoatDong.tenHoatDong}">
                                    </a>
                                </c:otherwise>
                            </c:choose>
                            <div class="custom-block">
                                <div class="custom-block-body">
                                    <c:choose>
                                        <c:when test="${isLoggedIn}">
                                            <h5 class="mb-3">
                                                <a href="${pageContext.request.contextPath}/activity/details/${hoatDong.maHoatDong}">
                                                    ${hoatDong.tenHoatDong}
                                                </a>
                                            </h5>
                                        </c:when>
                                        <c:otherwise>
                                            <h5 class="mb-3">
                                                <a href="${pageContext.request.contextPath}/sign-in?redirect=/activity/details/${hoatDong.maHoatDong}"
                                                   onclick="alert('Vui lòng đăng nhập để xem chi tiết hoạt động!')">
                                                    ${hoatDong.tenHoatDong}
                                                </a>
                                            </h5>
                                        </c:otherwise>
                                    </c:choose>
                                    <p>
                                        <c:choose>
                                            <c:when test="${not empty hoatDong.moTa}">
                                                ${hoatDong.moTa}
                                            </c:when>
                                            <c:otherwise>
                                                <strong>Địa điểm:</strong> ${hoatDong.diaDiem}<br/>
                                                <strong>Thời gian:</strong> ${hoatDong.getThoiGianBatDauFormatted()}
                                            </c:otherwise>
                                        </c:choose>
                                        <br/>
                                        <span class="badge ${hoatDong.trangThai == 'Đang hoạt động' ? 'bg-success' : (hoatDong.trangThai == 'Sắp diễn ra' ? 'bg-warning' : 'bg-secondary')}">
                                            ${hoatDong.trangThai}
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty danhSachHoatDong}">
                    <div class="col-12 text-center">
                        <p>Không có hoạt động nào để hiển thị. Vui lòng kiểm tra dữ liệu trong cơ sở dữ liệu hoặc backend.</p>
                    </div>
                </c:if>
            </div>
        </div>
    </section>

    <section class="volunteer-section section-padding" id="section_3">
        <div class="container">
            <div class="row">

                <!-- Cột trái: Thông tin cá nhân hoặc form đăng ký -->
                <div class="col-lg-6 col-12">
                    <c:choose>
                        <c:when test="${isLoggedIn and not empty loggedInProfile and not empty loggedInAccount}">
                            <h2 class="text-white mb-4">Xin chào, ${loggedInProfile.hoTen}!</h2>
                            <div class="card shadow-lg p-4 mb-4" style="background-color: #f8f9fa; border-radius: 15px;">
                                <h3 class="mb-4 text-dark border-bottom pb-2">Thông tin tài khoản của bạn</h3>

                                <div class="row">
                                    <div class="col-md-6 mb-3"><strong>Họ tên:</strong> ${loggedInProfile.hoTen}</div>
                                    <div class="col-md-6 mb-3"><strong>Email:</strong> ${loggedInAccount.email}</div>
                                    <div class="col-md-6 mb-3"><strong>Chức vụ:</strong> ${loggedInProfile.chucVu}</div>
                                    <div class="col-md-6 mb-3"><strong>SĐT:</strong> ${loggedInProfile.sdt}</div>
                                    <div class="col-md-6 mb-3"><strong>Địa chỉ:</strong> ${loggedInProfile.diaChi}</div>
                                    <div class="col-md-6 mb-3"><strong>Trạng thái:</strong> <span class="badge bg-primary">${loggedInProfile.trangThai}</span></div>                   
                                </div>
                            </div>
                        </c:when>

                        <c:otherwise>
                            <div class="alert alert-warning bg-light p-4 rounded shadow-sm d-flex align-items-center">
                                <i class="bi bi-exclamation-triangle-fill text-warning fs-3 me-3"></i>
                                <div>
                                    <h4 class="text-dark mb-2">Chào mừng đến hệ thống tình nguyện!</h4>
                                    <p class="text-dark mb-0">Vui lòng <a href="${pageContext.request.contextPath}/sign-in">đăng nhập</a> để xem thông tin.</p>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Cột phải: Hình ảnh và mô tả -->
                <div class="col-lg-6 col-12">
                    <c:choose>
                        <c:when test="${isLoggedIn and not empty loggedInProfile.urlAvatar}">
                            <img src="${pageContext.request.contextPath}${sessionScope.urlAvatar}" 
                                 alt="Avatar" 
                                 class="profile-img" 
                                 style="width: 100%; max-height: 400px; object-fit: cover; border-radius: 10px;" />

                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/src/images/default-avatar.png"
                                 class="volunteer-image img-fluid rounded shadow"
                                 alt="Ảnh mặc định"
                                 style="border-radius: 10px; object-fit: cover; width: 100%; max-height: 400px;">
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </div>
    </section>

    <section class="news-section section-padding" id="section_4">
        <div class="container">
            <div class="row">

                <!-- Tiêu đề -->
                <div class="col-lg-12 col-12 mb-5">
                    <h2>Tin tức mới</h2>
                </div>

                <!-- Tin ngẫu nhiên -->
                <div class="col-lg-7 col-12">
                    <c:forEach var="tin" items="${tinNgauNhien}">
                        <div class="news-block <c:if test='${!status.first}'>mt-3</c:if>">
                                <div class="news-block-top">
                                    <a href="chi-tiet-tin.jsp?id=${tin.id}">
                                    <img src="${pageContext.request.contextPath}/src/images/tintuc/${tin.hinhAnh}"  class="img-fluid"
                                         style="width: 100%; height: 300px; object-fit: cover; border-radius: 10px;"alt="Mô tả ảnh">

                                </a>
                                <div class="news-category-block">
                                    <a href="#" class="category-block-link">${tin.danhMuc}</a>
                                </div>
                            </div>

                            <div class="news-block-info">
                                <div class="d-flex mt-2">
                                    <div class="news-block-date">
                                        <i class="bi-calendar4 custom-icon me-1"></i>
                                        <fmt:formatDate value="${tin.ngayDang}" pattern="dd-MM-yyyy" />
                                    </div>

                                    <div class="news-block-author mx-5">
                                        <i class="bi-person custom-icon me-1"></i>${tin.tacGia}
                                    </div>

                                    <div class="news-block-comment">
                                        <i class="bi-chat-left custom-icon me-1"></i>${tin.soBinhLuan} bình luận
                                    </div>
                                </div>

                                <div class="news-block-title mb-2">
                                    <h4>
                                        <a href="chi-tiet-tin.jsp?id=${tin.id}" class="news-block-title-link">
                                            ${tin.tieuDe}
                                        </a>
                                    </h4>
                                </div>

                                <div class="news-block-body">
                                    <p>${tin.noiDung}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- Sidebar: Tin gần đây, danh mục, thẻ -->
                <div class="col-lg-4 col-12 mx-auto">

                    <!-- Tìm kiếm -->
                    <form class="custom-form search-form" action="#" method="post" role="form">
                        <input class="form-control" type="search" placeholder="Tìm kiếm..." aria-label="Search">
                        <button type="submit" class="form-control">
                            <i class="bi-search"></i>
                        </button>
                    </form>

                    <!-- Tin gần đây -->
                    <h5 class="mt-5 mb-3">Tin gần đây</h5>
                    <c:forEach var="recent" items="${tinGanDay}">
                        <div class="news-block news-block-two-col d-flex mt-4">
                            <div class="news-block-two-col-image-wrap">
                                <a href="chi-tiet-tin.jsp?id=${recent.id}">
                                    <img src="${pageContext.request.contextPath}/src/images/tintuc/${recent.hinhAnh}" alt="ảnh tin tức">
                                    </div>

                                    <div class="news-block-two-col-info">
                                        <div class="news-block-title mb-2">
                                            <h6>
                                                <a href="chi-tiet-tin.jsp?id=${recent.id}" class="news-block-title-link">
                                                    ${recent.tieuDe}
                                                </a>
                                            </h6>
                                        </div>
                                        <div class="news-block-date">
                                            <i class="bi-calendar4 custom-icon me-1"></i>
                                            <fmt:formatDate value="${recent.ngayDang}" pattern="dd-MM-yyyy" />
                                        </div>
                                    </div>
                            </div>
                        </c:forEach>

                        <!-- Danh mục -->
                        <div class="category-block d-flex flex-row flex-wrap gap-2 mt-5">
                            <h5 class="mb-3 w-100">Danh mục</h5>
                            <c:forEach var="dm" items="${danhMucs}">
                                <a href="#" class="category-block-link badge bg-secondary text-white px-3 py-2 rounded-pill">
                                    ${dm}
                                </a>
                            </c:forEach>
                        </div>

                        <!-- Tags -->
                        <div class="sidebar-section mt-4">
                            <h5 class="mb-3">Hashtag</h5>
                            <div class="d-flex flex-wrap gap-2">
                                <c:forEach var="tag" items="${hashtags}">
                                    <a href="#" class="badge bg-primary text-white px-3 py-2 rounded-pill text-decoration-none">
                                        #${tag}
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                        <!-- Newsletter -->
                        <form class="custom-form subscribe-form mt-5" action="#" method="post" role="form">
                            <h5 class="mb-4">Đăng ký nhận tin</h5>
                            <input type="email" name="subscribe-email" id="subscribe-email" pattern="[^ @]*@[^ @]*"
                                   class="form-control" placeholder="Địa chỉ email" required>
                            <div class="col-lg-12 col-12">
                                <button type="submit" class="form-control">Đăng ký</button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
    </section>
</main>

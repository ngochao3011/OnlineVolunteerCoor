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

    <section class="section-padding section-bg" id="section_2">
        <div class="container">
            <div class="row">

                <div class="col-lg-6 col-12 mb-5 mb-lg-0">
                    <img src="${pageContext.request.contextPath}/src/images/group-people-volunteering-foodbank-poor-people.jpg"
                         class="custom-text-box-image img-fluid" alt="">
                </div>

                <div class="col-lg-6 col-12">
                    <div class="custom-text-box">
                        <h2 class="mb-2">Our Story</h2>

                        <h5 class="mb-3">Online Volunteer Coor, Non-Profit Organization</h5>

                        <p class="mb-0">This is a Bootstrap 5.2.2 CSS template for charity organization websites.
                            You can feel free to use it. Please tell your friends about TemplateMo website. Thank
                            you.</p>
                    </div>

                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-12">
                            <div class="custom-text-box mb-lg-0">
                                <h5 class="mb-3">Our Mission</h5>

                                <p>Sed leo nisl, posuere at molestie ac, suscipit auctor quis metus</p>

                                <ul class="custom-list mt-2">
                                    <li class="custom-list-item d-flex">
                                        <i class="bi-check custom-text-box-icon me-2"></i>
                                        Charity Theme
                                    </li>

                                    <li class="custom-list-item d-flex">
                                        <i class="bi-check custom-text-box-icon me-2"></i>
                                        Semantic HTML
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="col-lg-6 col-md-6 col-12">
                            <div class="custom-text-box d-flex flex-wrap d-lg-block mb-lg-0">
                                <div class="counter-thumb">
                                    <div class="d-flex">
                                        <span class="counter-number" data-from="1" data-to="2009"
                                              data-speed="1000"></span>
                                        <span class="counter-number-text"></span>
                                    </div>

                                    <span class="counter-text">Founded</span>
                                </div>

                                <div class="counter-thumb mt-4">
                                    <div class="d-flex">
                                        <span class="counter-number" data-from="1" data-to="120"
                                              data-speed="1000"></span>
                                        <span class="counter-number-text">B</span>
                                    </div>

                                    <span class="counter-text">Donations</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>

    <section class="section-padding" id="section_3">
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

    <section class="volunteer-section section-padding" id="section_4">
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


    <section class="news-section section-padding" id="section_5">
        <div class="container">
            <div class="row">

                <div class="col-lg-12 col-12 mb-5">
                    <h2>Latest News</h2>
                </div>

                <div class="col-lg-7 col-12">
                    <div class="news-block">
                        <div class="news-block-top">
                            <a href="news-detail.html">
                                <img src="${pageContext.request.contextPath}/src/images/news/medium-shot-volunteers-with-clothing-donations.jpg"
                                     class="news-image img-fluid" alt="">
                            </a>

                            <div class="news-category-block">
                                <a href="#" class="category-block-link">
                                    Lifestyle,
                                </a>

                                <a href="#" class="category-block-link">
                                    Clothing Donation
                                </a>
                            </div>
                        </div>

                        <div class="news-block-info">
                            <div class="d-flex mt-2">
                                <div class="news-block-date">
                                    <p>
                                        <i class="bi-calendar4 custom-icon me-1"></i>
                                        October 12, 2036
                                    </p>
                                </div>

                                <div class="news-block-author mx-5">
                                    <p>
                                        <i class="bi-person custom-icon me-1"></i>
                                        By Admin
                                    </p>
                                </div>

                                <div class="news-block-comment">
                                    <p>
                                        <i class="bi-chat-left custom-icon me-1"></i>
                                        32 Comments
                                    </p>
                                </div>
                            </div>

                            <div class="news-block-title mb-2">
                                <h4><a href="news-detail.html" class="news-block-title-link">Clothing donation to
                                        urban area</a></h4>
                            </div>

                            <div class="news-block-body">
                                <p>Lorem Ipsum dolor sit amet, consectetur adipsicing kengan omeg kohm tokito
                                    Professional charity theme based on Bootstrap</p>
                            </div>
                        </div>
                    </div>

                    <div class="news-block mt-3">
                        <div class="news-block-top">
                            <a href="news-detail.html">
                                <img src="${pageContext.request.contextPath}/src/images/news/medium-shot-people-collecting-foodstuff.jpg"
                                     class="news-image img-fluid" alt="">
                            </a>

                            <div class="news-category-block">
                                <a href="#" class="category-block-link">
                                    Food,
                                </a>

                                <a href="#" class="category-block-link">
                                    Donation,
                                </a>

                                <a href="#" class="category-block-link">
                                    Caring
                                </a>
                            </div>
                        </div>

                        <div class="news-block-info">
                            <div class="d-flex mt-2">
                                <div class="news-block-date">
                                    <p>
                                        <i class="bi-calendar4 custom-icon me-1"></i>
                                        October 20, 2036
                                    </p>
                                </div>

                                <div class="news-block-author mx-5">
                                    <p>
                                        <i class="bi-person custom-icon me-1"></i>
                                        By Admin
                                    </p>
                                </div>

                                <div class="news-block-comment">
                                    <p>
                                        <i class="bi-chat-left custom-icon me-1"></i>
                                        35 Comments
                                    </p>
                                </div>
                            </div>

                            <div class="news-block-title mb-2">
                                <h4><a href="news-detail.html" class="news-block-title-link">Food donation area</a>
                                </h4>
                            </div>

                            <div class="news-block-body">
                                <p>Sed leo nisl, posuere at molestie ac, suscipit auctor mauris. Etiam quis metus
                                    elementum, tempor risus vel, condimentum orci</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4 col-12 mx-auto">
                    <form class="custom-form search-form" action="#" method="post" role="form">
                        <input class="form-control" type="search" placeholder="Search" aria-label="Search">

                        <button type="submit" class="form-control">
                            <i class="bi-search"></i>
                        </button>
                    </form>

                    <h5 class="mt-5 mb-3">Recent news</h5>

                    <div class="news-block news-block-two-col d-flex mt-4">
                        <div class="news-block-two-col-image-wrap">
                            <a href="news-detail.html">
                                <img src="${pageContext.request.contextPath}/src/images/news/africa-humanitarian-aid-doctor.jpg"
                                     class="news-image img-fluid" alt="">
                            </a>
                        </div>

                        <div class="news-block-two-col-info">
                            <div class="news-block-title mb-2">
                                <h6><a href="news-detail.html" class="news-block-title-link">Food donation area</a>
                                </h6>
                            </div>

                            <div class="news-block-date">
                                <p>
                                    <i class="bi-calendar4 custom-icon me-1"></i>
                                    October 16, 2036
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="news-block news-block-two-col d-flex mt-4">
                        <div class="news-block-two-col-image-wrap">
                            <a href="news-detail.html">
                                <img src="${pageContext.request.contextPath}/src/images/news/close-up-happy-people-working-together.jpg"
                                     class="news-image img-fluid" alt="">
                            </a>
                        </div>

                        <div class="news-block-two-col-info">
                            <div class="news-block-title mb-2">
                                <h6><a href="news-detail.html" class="news-block-title-link">Volunteering Clean</a>
                                </h6>
                            </div>

                            <div class="news-block-date">
                                <p>
                                    <i class="bi-calendar4 custom-icon me-1"></i>
                                    October 24, 2036
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="category-block d-flex flex-column">
                        <h5 class="mb-3">Categories</h5>

                        <a href="#" class="category-block-link">
                            Drinking water
                            <span class="badge">20</span>
                        </a>

                        <a href="#" class="category-block-link">
                            Food Donation
                            <span class="badge">30</span>
                        </a>

                        <a href="#" class="category-block-link">
                            Children Education
                            <span class="badge">10</span>
                        </a>

                        <a href="#" class="category-block-link">
                            Poverty Development
                            <span class="badge">15</span>
                        </a>

                        <a href="#" class="category-block-link">
                            Clothing Donation
                            <span class="badge">20</span>
                        </a>
                    </div>

                    <div class="tags-block">
                        <h5 class="mb-3">Tags</h5>

                        <a href="#" class="tags-block-link">
                            Donation
                        </a>

                        <a href="#" class="tags-block-link">
                            Clothing
                        </a>

                        <a href="#" class="tags-block-link">
                            Food
                        </a>

                        <a href="#" class="tags-block-link">
                            Children
                        </a>

                        <a href="#" class="tags-block-link">
                            Education
                        </a>

                        <a href="#" class="tags-block-link">
                            Poverty
                        </a>

                        <a href="#" class="tags-block-link">
                            Clean Water
                        </a>
                    </div>

                    <form class="custom-form subscribe-form" action="#" method="post" role="form">
                        <h5 class="mb-4">Newsletter Form</h5>

                        <input type="email" name="subscribe-email" id="subscribe-email" pattern="[^ @]*@[^ @]*"
                               class="form-control" placeholder="Email Address" required>

                        <div class="col-lg-12 col-12">
                            <button type="submit" class="form-control">Subscribe</button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </section>

    <section class="contact-section section-padding" id="section_6">
        <div class="container">
            <div class="row">

                <div class="col-lg-4 col-12 ms-auto mb-5 mb-lg-0">
                    <div class="contact-info-wrap">
                        <h2>Get in touch</h2>

                        <div class="contact-image-wrap d-flex flex-wrap">
                            <img src="${pageContext.request.contextPath}/src/images/avatar/pretty-blonde-woman-wearing-white-t-shirt.jpg"
                                 class="img-fluid avatar-image" alt="">

                            <div class="d-flex flex-column justify-content-center ms-3">
                                <p class="mb-0">Clara Barton</p>
                                <p class="mb-0"><strong>HR & Office Manager</strong></p>
                            </div>
                        </div>

                        <div class="contact-info">
                            <h5 class="mb-3">Contact Infomation</h5>

                            <p class="d-flex mb-2">
                                <i class="bi-geo-alt me-2"></i>
                                Akershusstranda 20, 0150 Oslo, Norway
                            </p>

                            <p class="d-flex mb-2">
                                <i class="bi-telephone me-2"></i>

                                <a href="tel: 305-240-9671">
                                    305-240-9671
                                </a>
                            </p>

                            <p class="d-flex">
                                <i class="bi-envelope me-2"></i>

                                <a href="mailto:info@yourgmail.com">
                                    donate@charity.org
                                </a>
                            </p>

                            <a href="#" class="custom-btn btn mt-3">Get Direction</a>
                        </div>
                    </div>
                </div>

                <div class="col-lg-5 col-12 mx-auto">
                    <form class="custom-form contact-form" action="#" method="post" role="form">
                        <h2>Contact form</h2>

                        <p class="mb-4">Or, you can just send an email:
                            <a href="#">info@charity.org</a>
                        </p>
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-12">
                                <input type="text" name="first-name" id="first-name" class="form-control"
                                       placeholder="Jack" required>
                            </div>

                            <div class="col-lg-6 col-md-6 col-12">
                                <input type="text" name="last-name" id="last-name" class="form-control"
                                       placeholder="Doe" required>
                            </div>
                        </div>

                        <input type="email" name="email" id="email" pattern="[^ @]*@[^ @]*" class="form-control"
                               placeholder="Jackdoe@gmail.com" required>

                        <textarea name="message" rows="5" class="form-control" id="message"
                                  placeholder="What can we help you?"></textarea>

                        <button type="submit" class="form-control">Send Message</button>
                    </form>
                </div>

            </div>
        </div>
    </section>
</main>

window.onload = () => {
	const container = document.getElementById('container');
	const switchLink = document.getElementById('switch-link');

	// Lấy context path, ví dụ /OnlineVolunteerCoor
	const contextPath = "/" + window.location.pathname.split("/")[1];

	function toggle() {
		container.classList.toggle('sign-in');
		container.classList.toggle('sign-up');

		if (container.classList.contains('sign-in')) {
			switchLink.setAttribute('href', contextPath + '/sign-up');
			history.replaceState(null, '', contextPath + '/sign-in');
		} else {
			switchLink.setAttribute('href', contextPath + '/sign-in');
			history.replaceState(null, '', contextPath + '/sign-up');
		}
	}

	// Gán vào global scope để onclick gọi được
	window.toggle = toggle;

	// Hiển thị đúng form theo URL khi tải trang
	if (window.location.pathname.endsWith("sign-up")) {
		container.classList.remove('sign-in');
		container.classList.add('sign-up');
		switchLink.setAttribute('href', contextPath + '/sign-in');
	} else {
		container.classList.remove('sign-up');
		container.classList.add('sign-in');
		switchLink.setAttribute('href', contextPath + '/sign-up');
	}
};
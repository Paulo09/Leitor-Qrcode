
<%@ page import="cadastro.Objeto" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="" http-equiv="refresh" content="15;URL=https://localhost:8443/inventario/objeto/qrcode">
		<g:set var="entityName" value="${message(code: 'objeto.label', default: 'Objeto')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		
		<div align="center">
		  <h1>${url}</h1>
		  ${aux}
		</div>

		<script type="text/javascript">
	
		var app = new Vue({
			  el: '#app',
			  data: {
			    scanner: null,
			    activeCameraId: null,
			    cameras: [],
			    scans: []
			  },
			  mounted: function () {
			    var self = this;
			    self.scanner = new Instascan.Scanner({ video: document.getElementById('preview'), scanPeriod: 5 });
			    self.scanner.addListener('scan', function (content, image) {
				  alert('Valor Qrcode')
				  $.ajax({method:"POST",url:"qrcode",data:{"valQrcode":content}})
			      self.scans.unshift({ date: +(Date.now()), content: content });
			    });
			    Instascan.Camera.getCameras().then(function (cameras) {
			      self.cameras = cameras;
			      if (cameras.length > 0) {
			        self.activeCameraId = cameras[0].id;
			        self.scanner.start(cameras[1]);
			      } else {
			        console.error('No cameras found.');
			      }
			    }).catch(function (e) {
			      console.error(e);
			    });
			  },
			  methods: {
			    formatName: function (name) {
				      return name || '(unknown)';
			    },
			    selectCamera: function (camera) {
			      this.activeCameraId = camera.id;
			      this.scanner.start(camera);
			    }
			  }
			});
	</script>
	
	</body>
</html>


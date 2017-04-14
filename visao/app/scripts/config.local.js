angular.module('config', [])

.constant
(
		'ENV', {
			name:							'development',
			oauth2_redirect:				'http://localhost:9000/',
			apiEndpoint:					'http://localhost:8080/service',
			wsEndpoint:					 	'ws://localhost:8080/service/websocket'
		}
)
;



angular.module('config', [])

.constant
(
		'ENV', {
			name:							'development',
			oauth2_redirect:				'http://localhost:9000/',
			apiEndpoint:					'http://localhost:8080/agentesdabolsa',
			wsEndpoint:					 	'ws://localhost:8080/agentesdabolsa/websocket'
		}
)
;



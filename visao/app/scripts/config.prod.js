angular.module('config', [])

.constant
(
		'ENV', {
			name:						'development',
			oauth2_redirect:			'http://agentesdabolsa.ddns.net',
			apiEndpoint:				'http://agentesdabolsa.ddns.net/service',
			wsEndpoint:					'ws://agentesdabolsa.ddns.net:8080/service/websocket'
		}
)
;



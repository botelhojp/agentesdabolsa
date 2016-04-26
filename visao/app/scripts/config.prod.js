angular.module('config', [])

.constant
(
		'ENV', {
			name:						'development',
			oauth2_redirect:				'http://agentesdabolsa.com.br',
			apiEndpoint:					'http://agentesdabolsa.com.br/s/agentesdabolsa',
			wsEndpoint:					 	'ws://agentesdabolsa.com.br:8080/agentesdabolsa/websocket'
		}
)
;



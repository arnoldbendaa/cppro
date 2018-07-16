/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
(function() {
	'use strict';

	angular
		.module('coreApp.components')
		.controller('ExceptionViewModalCtrl', ExceptionViewModalCtrl);

	/* @ngInject */
	function ExceptionViewModalCtrl($scope, $rootScope, $modalInstance, displayError) {


		$scope.cancel = cancel;
		$scope.ok = ok;
		var veilHide = false;
		
		activate();

		function ok() {
			$modalInstance.close();
		}

		function cancel() {
			$modalInstance.dismiss('cancel');
		}

		function activate() {
			if (typeof displayError.e === "undefined" || displayError.e === null || (typeof displayError.e.title === "undefined" && (displayError.e.errors === undefined || displayError.e.errors === null))) {
				switch (displayError.serverStatus) {
					case 300:
						$scope.error = {
							title: 'Code: 300, Error: Multiple Choices',
							message: 'The requested address refers to more than one file. Depending on how the server is configured, you get an error or a choice of which page you want.'
						};
						break;
					case 301:
						$scope.error = {
							title: 'Code: 301, Error: Moved Permanently',
							message: 'If the server is set up properly it will automatically redirect the reader to the new location of the file.'
						};
						break;
					case 302:
						$scope.error = {
							title: 'Code: 302, Error: Moved Temporarily',
							message: 'Page has been moved temporarily, and the new URL is available. You should be sent there by the server.'
						};
						break;
					case 303:
						$scope.error = {
							title: 'Code: 303, Error: See Other',
							message: 'This is a "see other" SRC. Data is somewhere else and the GET method is used to retrieve it.'
						};
						break;
					case 304:
						$scope.error = {
							title: 'Code: 304, Error: Not Modified',
							message: 'If the request header includes an if modified since parameter, this code will be returned if the file has not changed since that date. Search engine robots may generate a lot of these.'
						};
						break;
					case 305:
						$scope.error = {
							title: 'Code: 305, Error: Use Proxy',
							message: 'The recipient is expected to repeat the request via the proxy.'
						};
						break;
					case 400:
						$scope.error = {
							title: 'Code: 400, Error: Bad Request',
							message: 'There is a syntax error in the request, and it is denied.'
						};
						break;
					case 401:
						$scope.error = {
							title: 'Code: 401, Error: Authorization Required',
							message: 'The request header did not contain the necessary authentication codes, and the client is denied access.'
						};
						break;
					case 402:
						$scope.error = {
							title: 'Code: 402, Error: Payment Required',
							message: 'Payment is required. This code is not yet in operation.'
						};
						break;
					case 403:
						$scope.error = {
							title: 'Code: 403, Error: Forbidden',
							message: 'The client is not allowed to see a certain file. This is also returned at times when the server doesnt want any more visitors.'
						};
						break;
					case 404:
						$scope.error = {
							title: 'Code: 404, Error: Not Found',
							message: 'The requested file was not found on the server. Possibly because it was deleted, or never existed before. Often caused by misspellings of URLs.'
						};
						break;
					case 405:
						$scope.error = {
							title: 'Code: 405, Error: Method Not Allowed',
							message: 'The method you are using to access the file is not allowed.'
						};
						break;
					case 406:
						$scope.error = {
							title: 'Code: 406, Error: Not Acceptable',
							message: 'The requested file exists but cannot be used as the client system doesnt understand the format the file is configured for.'
						};
						break;
					case 407:
						$scope.error = {
							title: 'Code: 407, Error: Proxy Authentication Required',
							message: 'The request must be authorised before it can take place.'
						};
						break;
					case 408:
						$scope.error = {
							title: 'Code: 408, Error: Request Timed Out',
							message: 'The server took longer than its allowed time to process the request. Often caused by heavy net traffic.'
						};
						break;
					case 409:
						$scope.error = {
							title: 'Code: 409, Error: Conflicting Request',
							message: 'Too many concurrent requests for a single file.'
						};
						break;
					case 410:
						$scope.error = {
							title: 'Code: 410, Error: Gone',
							message: 'The file used to be in this position, but is there no longer.'
						};
						break;
					case 411:
						$scope.error = {
							title: 'Code: 411, Error: Content Length Required',
							message: 'The request is missing its Content-Length header.'
						};
						break;
					case 412:
						$scope.error = {
							title: 'Code: 412, Error: Precondition Failed',
							message: 'A certain configuration is required for this file to be delivered, but the client has not set this up.'
						};
						break;
					case 413:
						$scope.error = {
							title: 'Code: 413, Error: Request Entity Too Long',
							message: 'The requested file was too big to process.'
						};
						break;
					case 414:
						$scope.error = {
							title: 'Code: 414, Error: Request URI Too Long',
							message: 'The address you entered was overly long for the server.'
						};
						break;
					case 415:
						$scope.error = {
							title: 'Code: 415, Error: Unsupported Media Type',
							message: 'The filetype of the request is unsupported.'
						};
						break;
					case 500:
						$scope.error = {
							title: 'Code: 500, Error: Internal Server Error',
							message: 'Nasty response that is usually caused by a problem in your Perl code when a CGI program is run.'
						};
						break;
					case 501:
						$scope.error = {
							title: 'Code: 501, Error: Not Implemented',
							message: 'The request cannot be carried out by the server.'
						};
						break;
					case 502:
						$scope.error = {
							title: 'Code: 502, Error: Bad Gateway',
							message: 'The server youre trying to reach is sending back errors.'
						};
						break;
					case 503:
						$scope.error = {
							title: 'Code: 503, Error: Service Unavailable',
							message: 'The service or file that is being requested is not currently available.'
						};
						break;
					case 504:
						$scope.error = {
							title: 'Code: 504, Error: Gateway Timeout',
							message: 'The gateway has timed out. Like the 408 timeout error, but this one occurs at the gateway of the server.'
						};
						break;
					case 505:
						$scope.error = {
							title: 'Code: 505, Error: HTTP Version Not Supported',
							message: 'The HTTP protocol you are asking for is not supported.'
						};
						break;
				}
			} else {
				if (displayError.e.errors !== undefined & displayError.e.errors !== null) {
					veilHide = true;
					var msgs = displayError.e.errors;
					var message = "";
					for (var i = 0; i < msgs.length; i++) {
						message += "Errors in worksheet \"" + msgs[i][0] + "\"\n";
						for (var j = 0; j < msgs[i][1].length; j++) {
							message += " - " + msgs[i][1][j] + "\n";
						}
					}
					$scope.error = {
						title: displayError.e.message,
						message: message
					};
				} else {
					$scope.error = {
						title: displayError.e.title,
						message: displayError.e.message
					};
				}
			}
			$rootScope.$broadcast('veil:hide', {});
		}
	}

})();
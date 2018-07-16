/*
 * angular-loading-bar
 *
 * intercepts XHR requests and creates a loading bar.
 * Based on the excellent nprogress work by rstacruz (more info in readme)
 *
 * (c) 2013 Wes Cruver
 * License: MIT
 */


(function() {

'use strict';

// Alias the loading bar for various backwards compatibilities since the project has matured:
angular.module('angular-loading-bar', ['cfp.loadingBarInterceptor']);
angular.module('chieffancypants.loadingBar', ['cfp.loadingBarInterceptor']);


/**
 * loadingBarInterceptor service
 *
 * Registers itself as an Angular interceptor and listens for XHR requests.
 */
angular.module('cfp.loadingBarInterceptor', ['cfp.loadingBar'])
  .config(['$httpProvider', function ($httpProvider) {

    var interceptor = ['$q', '$cacheFactory', '$timeout', '$rootScope', '$log', 'cfpLoadingBar', function ($q, $cacheFactory, $timeout, $rootScope, $log, cfpLoadingBar) {

      /**
       * The total number of requests made
       */
      var reqsTotal = 0;

      /**
       * The number of requests completed (either successfully or not)
       */
      var reqsCompleted = 0;

      /**
       * The amount of time spent fetching before showing the loading bar
       */
      var latencyThreshold = cfpLoadingBar.latencyThreshold;

      /**
       * $timeout handle for latencyThreshold
       */
      var startTimeout;

      /**
       * The total number of requests made for cover mode
       */
      var reqsTotalCover = 0;

      /**
       * The number of requests completed (either successfully or not) for cover mode
       */
      var reqsCompletedCover = 0;

      /**
       * $timeout handle for latencyThreshold for cover mode
       */
      var startTimeoutCover;


      /**
       * calls cfpLoadingBar.complete() which removes the
       * loading bar from the DOM.
       */
      function setComplete(hideSpinner) {
        $timeout.cancel(startTimeout);
        cfpLoadingBar.complete(hideSpinner);
        reqsCompleted = 0;
        reqsTotal = 0;
      }

      /**
       * calls cfpLoadingBar.uncover() which removes the
       * vail, loader and statement from the DOM for cover mode.
       */
      function setCompleteCover() {
        $timeout.cancel(startTimeoutCover);
        cfpLoadingBar.uncover();
        reqsCompletedCover = 0;
        reqsTotalCover = 0;
      }

      /**
       * Determine if the response has already been cached
       * @param  {Object}  config the config option from the request
       * @return {Boolean} retrns true if cached, otherwise false
       */
      function isCached(config) {
          //return false;
        var cache;
        var defaultCache = $cacheFactory.get('$http');
        var defaults = $httpProvider.defaults;

        // Choose the proper cache source. Borrowed from angular: $http service
        if ((config.cache || defaults.cache) && config.cache !== false &&
          (config.method === 'GET' || config.method === 'JSONP')) {
            cache = angular.isObject(config.cache) ? config.cache
              : angular.isObject(defaults.cache) ? defaults.cache
              : defaultCache;
        }

        var cached = cache !== undefined ?
          cache.get(config.url) !== undefined : false;

        if (config.cached !== undefined && cached !== config.cached) {
          return config.cached;
        }
        config.cached = cached;
        return cached;
      }


      return {
        'request': function(config) {
          // Check to make sure this request hasn't already been cached and that
          // the requester didn't explicitly ask us to ignore this request:
          if (!config.ignoreLoadingBar && !isCached(config)) {
              if(config.coverAllView === true) {
                  if (reqsTotalCover === 0) {
                      startTimeoutCover = $timeout(function() {
                          cfpLoadingBar.cover(config.statement);
                    }, latencyThreshold);
                  }
                  reqsTotalCover++;
              } else {
                  if (reqsTotal === 0) {
                    startTimeout = $timeout(function() {
                        var displaySpinner = false;
                        if(config.displaySpinner === true) {
                            displaySpinner = true;
                        }
                        cfpLoadingBar.start(displaySpinner);
                    }, latencyThreshold);
                  }
                  reqsTotal++;
                  cfpLoadingBar.set(reqsCompleted / reqsTotal);
              }
          }
          return config;
        },

        'response': function(response) {
          if (!response || !response.config) {
            $log.error('Broken interceptor detected: Config object not supplied in response:\n https://github.com/chieffancypants/angular-loading-bar/pull/50');
            return response;
          }

          if (!response.config.ignoreLoadingBar && !isCached(response.config)) {
              if(response.config.coverAllView === true) {
                  reqsCompletedCover++;
                  if (reqsCompletedCover >= reqsTotalCover) {
                      setCompleteCover();
                  }
              } else {
                  reqsCompleted++;
                  $rootScope.$broadcast('cfpLoadingBar:loaded', {url: response.config.url, result: response});
                  if (reqsCompleted >= reqsTotal) {
                      var hideSpinner = false;
                      if(response.config.displaySpinner === true) {
                          hideSpinner = true;
                      }
                      setComplete(hideSpinner);
                  } else {
                    cfpLoadingBar.set(reqsCompleted / reqsTotal);
                  }
              }
          }
          return response;
        },

        'responseError': function(rejection) {
          if (!rejection || !rejection.config) {
            $log.error('Broken interceptor detected: Config object not supplied in rejection:\n https://github.com/chieffancypants/angular-loading-bar/pull/50');
            return $q.reject(rejection);
          }

          if (!rejection.config.ignoreLoadingBar && !isCached(rejection.config)) {
              if(rejection.config.coverAllView === true) {
                  reqsCompletedCover++;
                  if (reqsCompletedCover >= reqsTotal) {
                      setCompleteCover();
                  }
              } else {
                  reqsCompleted++;
                  $rootScope.$broadcast('cfpLoadingBar:loaded', {url: rejection.config.url, result: rejection});
                  if (reqsCompleted >= reqsTotal) {
                      var hideSpinner = false;
                      if(rejection.config.displaySpinner === true) {
                          hideSpinner = true;
                      }
                      setComplete(hideSpinner);
                  } else {
                    cfpLoadingBar.set(reqsCompleted / reqsTotal);
                  }
              }
          }
          return $q.reject(rejection);
        }
      };
    }];

    $httpProvider.interceptors.push(interceptor);
  }]);


/**
 * Loading Bar
 *
 * This service handles adding and removing the actual element in the DOM.
 * Generally, best practices for DOM manipulation is to take place in a
 * directive, but because the element itself is injected in the DOM only upon
 * XHR requests, and it's likely needed on every view, the best option is to
 * use a service.
 */
angular.module('cfp.loadingBar', [])
  .provider('cfpLoadingBar', function() {

    this.autoIncrement = true;
//    this.includeSpinner = false;
    this.includeBar = true;
    this.latencyThreshold = 100;
    this.startSize = 0.02;
    this.parentSelector = 'body';
    this.spinnerTemplate = '<div id="loading-bar-spinner"><div class="spinner-icon"></div></div>';
    this.loadingBarTemplate = '<div id="loading-bar"><div class="bar"><div class="peg"></div></div></div>';
    this.veilAndLoadingBarTemplate = '<div id="veil"></div><div id="veil-statement-container"><div id="veil-statement"><h1>Loading data, please wait...</h1><i class="fa fa-refresh fa-5x fa-spin"></i></div></div>';


    this.$get = ['$injector', '$document', '$timeout', '$rootScope', function ($injector, $document, $timeout, $rootScope) {
        var $animate;
        var $parentSelector = this.parentSelector,
          loadingBarContainer = angular.element(this.loadingBarTemplate),
          loadingBar = loadingBarContainer.find('div').eq(0),
          spinner = angular.element(this.spinnerTemplate),
          veilAndLoadingBarContainer = angular.element(this.veilAndLoadingBarTemplate);

        var incTimeout,
          completeTimeout,
          started = false,
          status = 0;

        var autoIncrement = this.autoIncrement;
//        var includeSpinner = this.includeSpinner;
        var includeBar = this.includeBar;
        var startSize = this.startSize;

        /**
         * Inserts the loading bar element into the dom, and sets it to 2%
         */
        function _start(displaySpinner) {
          if (!$animate) {
            $animate = $injector.get('$animate');
          }

          var $parent = $document.find($parentSelector).eq(0);
          $timeout.cancel(completeTimeout);

          // do not continually broadcast the started event:
          if (started) {
            return;
          }

          $rootScope.$broadcast('cfpLoadingBar:started');
          started = true;

          if (includeBar) {
            $animate.enter(loadingBarContainer, $parent, angular.element($parent[0].lastChild));
          }

          if (displaySpinner) {
            $animate.enter(spinner, $parent, angular.element($parent[0].lastChild));
          }

          _set(startSize);
      }

      /**
       * Set the loading bar's width to a certain percent.
       *
       * @param n any value between 0 and 1
       */
      function _set(n) {
        if (!started) {
          return;
        }
        var pct = (n * 100) + '%';
        loadingBar.css('width', pct);
        status = n;

        // increment loadingbar to give the illusion that there is always
        // progress but make sure to cancel the previous timeouts so we don't
        // have multiple incs running at the same time.
        if (autoIncrement) {
          $timeout.cancel(incTimeout);
          incTimeout = $timeout(function() {
            _inc();
          }, 250);
        }
      }

      /**
       * Increments the loading bar by a random amount
       * but slows down as it progresses
       */
      function _inc() {
        if (_status() >= 1) {
          return;
        }

        var rnd = 0;

        // TODO: do this mathmatically instead of through conditions

        var stat = _status();
        if (stat >= 0 && stat < 0.25) {
          // Start out between 3 - 6% increments
          rnd = (Math.random() * (5 - 3 + 1) + 3) / 100;
        } else if (stat >= 0.25 && stat < 0.65) {
          // increment between 0 - 3%
          rnd = (Math.random() * 3) / 100;
        } else if (stat >= 0.65 && stat < 0.9) {
          // increment between 0 - 2%
          rnd = (Math.random() * 2) / 100;
        } else if (stat >= 0.9 && stat < 0.99) {
          // finally, increment it .5 %
          rnd = 0.005;
        } else {
          // after 99%, don't increment:
          rnd = 0;
        }

        var pct = _status() + rnd;
        _set(pct);
      }

      function _status() {
        return status;
      }

      function _completeAnimation() {
        status = 0;
        started = false;
      }

      function _complete(hideSpinner) {
        if (!$animate) {
          $animate = $injector.get('$animate');
        }

        $rootScope.$broadcast('cfpLoadingBar:completed');
        _set(1);

        $timeout.cancel(completeTimeout);

        // Attempt to aggregate any start/complete calls within 500ms:
        completeTimeout = $timeout(function() {
          var promise = $animate.leave(loadingBarContainer, _completeAnimation);
          if (promise && promise.then) {
            promise.then(_completeAnimation);
          }
          if (hideSpinner) {
              $animate.leave(spinner);
          }
        }, 500);
      }
      
      var completedTimeoutCover,
          startedCover = false;
      
      function _cover(statement) {
          if (!$animate) {
              $animate = $injector.get('$animate');
          }
          
          var $parent = $document.find($parentSelector).eq(0);
          $timeout.cancel(completedTimeoutCover);
  
          // do not continually broadcast the started event:
          if (startedCover) {
            return;
          }
  
          $rootScope.$broadcast('cfpLoadingBar:startedCover');
          startedCover = true;
  
          if(statement !== null && statement !== undefined && statement !== "") {
              angular.element(veilAndLoadingBarContainer[1]).find("h1").html(statement);
          }
          
          $animate.enter(veilAndLoadingBarContainer, $parent, angular.element($parent[0].lastChild));
      }
      
      function _uncover() {
          if (!$animate) {
              $animate = $injector.get('$animate');
          }

          $rootScope.$broadcast('cfpLoadingBar:endedCover');
          _set(1);

          $timeout.cancel(completedTimeoutCover);

          $animate.leave(veilAndLoadingBarContainer);
          startedCover = false;
      }

      return {
        start            : _start,
        set              : _set,
        status           : _status,
        inc              : _inc,
        complete         : _complete,
        cover            : _cover,
        uncover          : _uncover,
        autoIncrement    : this.autoIncrement,
//        includeSpinner   : this.includeSpinner,
        latencyThreshold : this.latencyThreshold,
        parentSelector   : this.parentSelector,
        startSize        : this.startSize
      };


    }];     //
  });       // wtf javascript. srsly
})();       //

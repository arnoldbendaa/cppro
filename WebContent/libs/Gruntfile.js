module.exports = function(grunt) {
    var destination = 'wijmo/SpreadJS/3.20142.13/js/';
    var version = '1';
    var file = 'jquery.wijmo.wijspread.all.3.20142.13.v' + version;

    // Project configuration.
    grunt.initConfig({
        uglify: {
            build: {
                src: destination + file + '.js',
                dest: destination + file + '.min.js'
            }
        }
    });

    // cssmin: {
    //     my_target: {
    //         files: [{
    //             expand: true,
    //             cwd: 'css/',
    //             src: ['*.css', '!*.min.css'],
    //             dest: 'css/',
    //             ext: '.min.css'
    //         }]
    //     }
    // }

    // Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-uglify');
    //grunt.loadNpmTasks('grunt-contrib-cssmin');

    // Default task(s).
    grunt.registerTask('default', ['uglify']);

};
/**
 * set-eventspriorities.js
 *
 * 競技の優先度を設定する。
 */

var async = require('async');

var contestRef = require('./contestref.js').ref;

// 競技と優先度のリスト
var EventsPriorities = {
    'efto': 100.0,
};

var setEventsPriorities = function() {
    async.each(Object.keys(EventsPriorities), function(eid, next) {
        var priority = EventsPriorities[eid];
        contestRef.child('events').child(eid).setPriority(priority, function(error) {
            if (error) {
                console.error(error);
            } else {
                console.log(eid + ': ' + priority);
                next();
            }
        });
    }, function (err) {
        if (err) {
            console.error(err);
        } else {
            console.log('Complete!');
            process.exit(0);
        }
    });
};
setEventsPriorities();

'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const Surepay = core.registerPlugin('Surepay', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.SurepayWeb()),
});

class SurepayWeb extends core.WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    SurepayWeb: SurepayWeb
});

exports.Surepay = Surepay;
//# sourceMappingURL=plugin.cjs.js.map

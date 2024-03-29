'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const Surepay = core.registerPlugin('Surepay', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.SurepayWeb()),
});

class SurepayWeb extends core.WebPlugin {
    async printOnSurepay(options) {
        return options;
    }
    async getBase64(options) {
        return options;
    }
    async enableBluetoothListenerService(value) {
        return value;
    }
    async disableBluetoothListnerService(value) {
        return value;
    }
    async getSurepayConnectionStatus(value) {
        return value;
    }
    async getConnectedDeviceInfo(value) {
        return value;
    }
    async submitTransaction(options) {
        return options;
    }
    async getLastTransactionDetails(value) {
        return value;
    }
    async showLastTransactionDetails(value) {
        return value;
    }
    async getPrinterStatus(value) {
        return value;
    }
    async exportLastTransaction(value) {
        return value;
    }
    async clearLastTransaction(value) {
        return value;
    }
    async OnNewReceiptFromSurepayDevice(value) {
        return value;
    }
    OnConnectionLost(value) {
        return value;
    }
    OnConnectionEstablished(value) {
        return value;
    }
    async OnError(value) {
        return value;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    SurepayWeb: SurepayWeb
});

exports.Surepay = Surepay;
//# sourceMappingURL=plugin.cjs.js.map

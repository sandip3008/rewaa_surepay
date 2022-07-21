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
    async printOnSurepay(options) {
        console.log('printOnSurepay web 15', options);
        return options;
    }
    async enableBluetoothListenerService(value) {
        console.log('enableBluetoothListenerService web 13', value);
        return value;
    }
    async disableBluetoothListnerService(value) {
        console.log('disableBluetoothListnerService', value);
        return value;
    }
    async getSurepayConnectionStatus(value) {
        console.log('getSurepayConnectionStatus', value);
        return value;
    }
    async getConnectedDeviceInfo(value) {
        console.log('getConnectedDeviceInfo', value);
        return value;
    }
    async submitTransaction(options) {
        console.log('submitTransaction amount: ', options);
        return options;
    }
    async getLastTransactionDetails(value) {
        console.log('getLastTransactionDetails', value);
        return value;
    }
    async showLastTransactionDetails(value) {
        console.log('showLastTransactionDetails', value);
        return value;
    }
    async getPrinterStatus(value) {
        console.log('getPrinterStatus web 7', value);
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

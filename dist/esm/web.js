import { WebPlugin } from '@capacitor/core';
export class SurepayWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
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
    async submitTransaction(value) {
        console.log('submitTransaction amount: ', value);
        return value;
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
//# sourceMappingURL=web.js.map
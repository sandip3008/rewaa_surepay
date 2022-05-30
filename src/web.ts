import { WebPlugin } from '@capacitor/core';

import type { SurepayPlugin } from './definitions';

export class SurepayWeb extends WebPlugin implements SurepayPlugin {
  
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async enableBluetoothListenerService(value: string): Promise<string> {
    console.log('enableBluetoothListenerService web 13', value);
    return value;
  }

  async disableBluetoothListnerService(value: boolean): Promise<boolean> {
    console.log('disableBluetoothListnerService', value);
    return value;
  }

  async getSurepayConnectionStatus(value: boolean): Promise<boolean> {
    console.log('getSurepayConnectionStatus', value);
    return value;
  }

  async getConnectedDeviceInfo(value: string): Promise<string> {
    console.log('getConnectedDeviceInfo', value);
    return value;
  }

  async submitTransaction(value: string): Promise<string> {
    console.log('submitTransaction amount: ', value);
    return value;
  }

  async getLastTransactionDetails(value: string): Promise<string> {
    console.log('getLastTransactionDetails', value);
    return value;
  }

  async showLastTransactionDetails(value: string): Promise<string> {
    console.log('showLastTransactionDetails', value);
    return value;
  }

  async getPrinterStatus(value: string): Promise<string> {
    console.log('getPrinterStatus web 7', value);
    return value;
  }

  async exportLastTransaction(value: boolean): Promise<boolean> {
    return value;
  }

  async clearLastTransaction(value: boolean): Promise<boolean> {
    return value;
  }
  
  async OnNewReceiptFromSurepayDevice(value: string): Promise<string> {
    return value;
  }

  OnConnectionLost(value: any): Promise<void> {
    return value;
  }

  OnConnectionEstablished(value: any): Promise<void> {
    return value;
  }
  
  async OnError(value: string): Promise<string> {
    return value;
  }
}

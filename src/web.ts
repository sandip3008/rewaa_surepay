import { WebPlugin } from '@capacitor/core';

import type { SurepayPlugin } from './definitions';

export class SurepayWeb extends WebPlugin implements SurepayPlugin {
  
  async printOnSurepay(options: { content: string, type: string }): Promise<{ content: string; type: string }> {    
    return options;
  }

  async getBase64(options: { content: string, isTestEnv: boolean }): Promise<{ content: string, isTestEnv: boolean }> {    
    return options;
  }

  async enableBluetoothListenerService(value: string): Promise<string> {    
    return value;
  }

  async disableBluetoothListnerService(value: boolean): Promise<boolean> {    
    return value;
  }

  async getSurepayConnectionStatus(value: boolean): Promise<boolean> {    
    return value;
  }

  async getConnectedDeviceInfo(value: string): Promise<string> {    
    return value;
  }

  async submitTransaction(options: { amount: string }): Promise<{ amount: string }> {    
    return options;
  }

  async getLastTransactionDetails(value: string): Promise<string> {    
    return value;
  }

  async showLastTransactionDetails(value: string): Promise<string> {
    return value;
  }

  async getPrinterStatus(value: string): Promise<string> {
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

import { registerPlugin } from '@capacitor/core';

import type { SurepayPlugin } from './definitions';

const Surepay = registerPlugin<SurepayPlugin>('Surepay', {
  web: () => import('./web').then(m => new m.SurepayWeb()),
});

export * from './definitions';
export { Surepay };

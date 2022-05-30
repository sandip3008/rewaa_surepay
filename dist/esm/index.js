import { registerPlugin } from '@capacitor/core';
const Surepay = registerPlugin('Surepay', {
    web: () => import('./web').then(m => new m.SurepayWeb()),
});
export * from './definitions';
export { Surepay };
//# sourceMappingURL=index.js.map
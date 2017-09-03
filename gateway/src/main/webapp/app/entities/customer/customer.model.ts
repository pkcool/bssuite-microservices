import { BaseEntity } from './../../shared';

export class Customer implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public tradingName?: string,
        public abn?: string,
    ) {
    }
}

import { BaseEntity } from './../../shared';

export class CustomerCategory implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public tradingName?: string,
    ) {
    }
}

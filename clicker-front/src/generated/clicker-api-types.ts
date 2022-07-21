/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.36.1070 on 2022-07-21 13:57:48.

export interface GameRepresentation {
    id: string;
    money: number;
    player: PlayerRepresentation;
    manualClickCount: number;
    generators: GeneratorRepresentation[];
    createdAt: Date;
    updatedAt: Date;
}

export interface GeneratorRepresentation {
    id: string;
    level: number;
    name: string;
    description: string;
    generatedClick: number;
    baseCost: number;
    baseMultiplier: number;
    baseClickPerSecond: number;
    createdAt: Date;
    updatedAt: Date;
}

export interface ManualClickRepresentation {
    manualClickCount: number;
}

export interface PlayerRepresentation {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    pseudonym: string;
    role: UserRoleType;
}

export const enum UserRoleType {
    ADMIN = "ADMIN",
    USER = "USER",
}

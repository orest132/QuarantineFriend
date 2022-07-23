export class UserModel {
    public id: number;
    public role: Role;
    public imageUrl: string;
  
    constructor(
      public firstName: string,
      public lastName: string,
      public email: string,
      public jobPosition: string,
      public username: string
    ) {}
  }
  
  export class Role {
    public id: number;
    public name: string;
    public authorities: Authority[];
  }

  export class Authority {
    public id: number;
    public name: string;
    public authority: string;
  }
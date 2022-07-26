export class UserModel {
    public id: number;
    public role: Role;
    public imageUrl: string;
  
    constructor(
      public firstName: string,
      public lastName: string,
      public email: string,
      public jobPosition: string,
      public username: string,
      public hobbies: Hobby[]
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
  export class RequestModel {
    public id: number;
    public from_user: UserModel;
    public to_user: UserModel;
  }

  export class Message {
    public id: number;
    public message: string;
    public user: UserModel;
  }

  export class ChatModel {
    public id: number;
    public user1: UserModel;
    public user2: UserModel;
    public messages:Message[];
  }

  export class Hobby {
    public id: number;
    public hobby: string;

  }


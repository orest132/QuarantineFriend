export class UserModel {
    public id: number;
    public role: Role;
    public imageUrl: string;
    public isBanned: boolean;
  
    constructor(
      public firstName: string,
      public lastName: string,
      public email: string,
      public age: number,
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

  export class ReportModel {
    public id: number;
    public reporter: UserModel;
    public reportee: UserModel;
    public report: string;
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
    public chatActiveByUser1:number;
  }

  export class Hobby {
    public id: number;
    public hobby: string;

  }


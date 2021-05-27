classDiagram
Main --> App
App --> MenuController
ExitMenuAction ..o MemberSubMenuMenuAction
ExitMenuAction --|> MenuAction
CreateMemberMenuAction --|> MenuAction
CreateMemberMenuAction --> MemberController
MemberSubMenuMenuAction --> MenuController
CreateMemberMenuAction ..o MemberSubMenuMenuAction



MemberController o-- MemberView
MemberController --> MemberModel
MemberController --> MembershipModel
MembershipController --> MembershipModel
MembershipController --> MemberModel
MenuController o-- MenuView
MenuController --> MenuModel

InputModel --> MemberModel
InputModel --> CompetitionModel
MemberModel --> MembershipModel




InputController <.. MemberController
InputController <.. MenuController

MemberService --> FileService
MemberService ..o MemberModel


MemberView --|> View
MenuView --|> View

class MemberController{
    -MemberView
    -MembershipController
    -ArrayList<MemberModel>
    +createMember()
    +addMember(id, name, mail, gender, birthday, phone, competitive, active)
    -gendersToArray() String[]
    -renewExpiringMembers()
    +requestPaymentForUnpaidMembers()
    +removeMemberFromList(members) ArrayList<MemberModel>
    +getMemberByID(id, members) MenuModel
    +getMemberByID(id) MenuModel
    +saveMembers()
    +loadMembers() MemberModel[]
    +membersToStringArray(members) ArrayList<MemberModel>
    -getMemberLine(member) String[]
    -getMemberHeader() String[]
    +viewTableMembers(member)
    +viewTableMembers(members)
    +viewTableMembers()
    +getColumnWidth() int[]
    +searchMember()
    +viewMemberById()
    +getMemberById(id) MemberModel
    +viewMembersByName()
    +getMembersByName(name) ArrayList<MemberModel>
    +viewMembersByMail()
    +getMembersByMail(mail) ArrayList<MemberModel>
    +viewMemberByPhoneNumber()
    +getMemberByPhoneNumber(phoneNumber) ArrayList<MemberModel>
    +anonymizeMember()
    +editMember()
    +generateMemberId() String
    +getExpiringMembers(memberModels,days) ArrayList<MemberModel>
    +getUnpaidMembers(ArrayList<MemberModel> memberModels) ArrayList<MemberModel>
}
class MembershipController{
  +addActiveMembership(member)
  +addPassiveMembership(member)
  -generateMembershipId(member) String
  +renewMembership(member, durationYears)
  -createNewMembership() MembershipModel
}
class MenuController{
    -MenuView
    -MenuModel
    +Run()
}
class InputModel{
  +isValidDate(date)$ boolean
  +isValidCompetitionTime(time)$ boolean
  +isValidCompetitionResultTime(time)$ boolean
  +isValidName(text)$ boolean
  +isValidMail(text)$ boolean
  +isValidPhoneNumber(text)$ boolean
  +isValidRange(in, min, max)$ boolean
  +isValidMemberId(in, members)$ boolean
  +isValidCompetitionId(in, competitions)$ boolean

}
class InputController{
    -Scanner
    -InputView
    +promptYesNo() boolean
    +validateInteger() int
    +validateOptionRange(max) int
    +validateName() String
    +validateMail() String
    +validateDate() String
    +validatePhoneNumber() String
    +validateMemberId(members) String
    +validateCompetitionsId(competitions) CompetitionModel
    +validateCompetitionResultTime() String
    +validateCompetitionTime() String
    +validatePlacement() String
    +anyString() String
}
class MemberModel{
    -id:String
    -name:String
    -Create_Date:LocalDate
    -birthdate:LocalDate
    -gender:GenderType
    -phonenumber:String
    -mail:String
    -ageGroup:String
    -competitive:boolean
    -disciplines:ArrayList<DisciplineModel>
    -memberships:ArrayList<MembershipModel>
    -deleted:boolean
    +getAgeGroup() String
    +getLatestMembership() MembershipModel
    +getAge() int
}
class MembershipModel{
    -id:String
    -creationDate:LocalDate
    -startingDate:LocalDate
    -expirtingDate:LocalDate
    -paid:boolean
    -active:boolean
}
class MenuModel{
    -MENU_ACTIONS:MenuAction[]
    -MENU_HEADER:String
    -LOAD_TEXT:String
}


class MemberView{
  displayMember(member, columnWidth)
}
class MenuView{
  +printMenuOptions(header, menuAction)
}
class View{
  +printInline(text)
  +print(String text)
  +print(text, color)
  +printSuccess(text)
  +printWarning(text)
  +printInlineWarning(text)
  +displayOptions(String[] text)
}
<<abstract>> View

class App
class Main


class FileService{
    -FILE:File
    -PATH:String
    +readFromFile() String[]
    +writeToFile(input) String[]
    -createFileOnPath()
    +writeToBin(byte[] bytes)
    +loadFromBin() byte[]
    +readTextFile() ArrayList<String>
}
class MemberService{
    -FILE_SERVICE:FileService
    +save(members)
    +load() MemberModel[]
}


class CreateMemberMenuAction
class ExitMenuAction
class MemberSubMenuMenuAction
class MenuAction

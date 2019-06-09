//logs.js
var app = getApp()
Page({
  data: {
    userInfo: {},
    sessionID: '',
    nickname:'请输入昵称',
    name:'请输入姓名',
    school:'请输入学校',
    phone:'请输入手机号码',
    image_url:'',
  },
  onLoad(options) {
    var id = wx.getStorageSync('SessionId')
    var that = this

    if (app.globalData.userInfo) {
      that.setData({
        userInfo: app.globalData.userInfo,
      })
    }
    that.setData({
      sessionID: id,
    })
    wx.request({
      url: 'https://moneydog.club:3336/User/Info',
      method: 'GET',
      header: {
        "content-type": "application/x-www-form-urlencoded",
        sessionId: that.data.sessionID.toString()
      },
      success: function (res) {
        console.log(res.data)
        if(res.data.realname){
          that.setData({
            name:res.data.realname,
            nickname:res.data.falsename,
            school:res.data.school,
            phone:res.data.phoneNum
          })
        }
        
      }
    })
  },
  onReady() {
    // Do something when page ready.
  },
  onShow() {
    // Do something when page show.
  },
  onHide() {
    // Do something when page hide.
  },
  onUnload() {
    // Do something when page close.
  },
  setNickname: function(e){
    this.setData({
      nickname: e.detail.value
    })
  },
  setName: function (e) {
    this.setData({
      name: e.detail.value
    })
  },
  setSchool: function (e) {
    this.setData({
      school: e.detail.value
    })
  },
  setPhone: function (e) {
    this.setData({
      phone: e.detail.value
    })
  },
  change_info:function(){
  var that = this 
  wx.request({
    url: 'https://moneydog.club:3336/User/Update',
    method:'POST',
    header: {
      "content-type": "application/x-www-form-urlencoded",
      sessionId:that.data.sessionID.toString()
    },
    data: {
      nickname: that.data.nickname,
      name: that.data.name,
      school: that.data.school,
      phone: that.data.phone,
      image_url:that.data.image_url
    },
    success: function (res) {
      if(res){
      console.log(res)
      wx.showModal({
        title: '修改成功',
        content: '请确认信息是否真实',
      })
      }
    }
  })
  },
  upSchoolcard:function(){
    var that = this
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths

        that.setData({
          image_url:tempFilePaths[0]
        })
        wx.uploadFile({
          url: 'http://119.23.218.7:8080/File/Upload',
          filePath: that.data.image_url,
          name: 'img',
          success:function(res){
            if(res){
              var res_data = JSON.parse(res.data)
              that.setData({
                image_url:"http://119.23.218.7:8080/"+res_data.imageUrl
              })

            }
          }
        })

      }
    })  
  }

})

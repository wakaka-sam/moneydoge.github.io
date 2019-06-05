//logs.js
var app = getApp()
Page({
  data: {
    sessionID: '',
    user_img:  '',
    nickname:'',
    name:'',
    school:'',
    phone:'',
    image_url:''
  },
  onLoad(options) {
    // Do some initialize when page load.
    this.setData({
      sessionID: '847694c4-14dd-47b2-8922-facd8e379f47',
      user_img: 'http://img.52z.com/upload/news/image/20180213/20180213062641_35687.jpg',
      image_url: '../../../images/upload_picture.png'
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
      user_img:that.data.user_img,
      nickname: that.data.nickname,
      name: that.data.name,
      school: that.data.school,
      phone: that.data.phone,
      image_url:that.data.image_url
    },
    success: function (res) {
      console.log("修改成功")
      wx.navigateBack({
        delta: 1
      })
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
          url: 'https://119.23.218.7:8080/File/Upload',
          filePath: that.data.image_url,
          name: 'img',
          success: function (res) {
            var t = JSON.parse(res.data);
            console.log(t.imageUrl)
            var url = 'http://119.23.218.7:8080/' + t.imageUrl;
            that.setData({
              image_url: url
            })
          }
        })
      }
    })  
  },
  upUserimag: function () {
    var that = this
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        that.setData({
          user_img: tempFilePaths[0]
        })
        that.set
        wx.uploadFile({
          url: 'https://119.23.218.7:8080/File/Upload',
          filePath: that.data.user_img,
          name: 'img',
          success: function (res) {
            var t = JSON.parse(res.data);
            console.log(t.imageUrl)
            var url = 'https://119.23.218.7:8080/' + t.imageUrl;
            that.setData({
              user_img: url
            })
          }
        })
      }
    })
  }

})

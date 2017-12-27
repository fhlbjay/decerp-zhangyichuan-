package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Gift;
import cn.wolfcode.crm.query.MyGiftQueryObject;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.service.IGiftService;
import cn.wolfcode.crm.util.JsonResult;
import cn.wolfcode.crm.util.UploadUtil;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;


@Controller
@RequestMapping("gift")
public class GiftController {
    @Autowired
    IGiftService giftService;
    @Autowired
    ServletContext ctx;

    @RequestMapping("list")
    public String list(Model model, @ModelAttribute("qo") MyGiftQueryObject qo) {
        MyPageResult result = giftService.query(qo);
        model.addAttribute("result", result);
        return "gift/list";
    }

    @RequestMapping("input")
    public String input(Model model, Long id) {
        Gift gift = new Gift();
        if (id != null) {
            gift = giftService.selectByPrimaryKey(id);
        }

        model.addAttribute("entity", gift);

        return "gift/input";
    }

    @ResponseBody
    @RequestMapping("saveOrUpdate")
    public Object saveOrUpdate(Model model, Gift gift, MultipartFile pic) {

        if (gift.getSurplus() != null) {
            if (gift.getSurplus() > gift.getCount()) {
                return new JsonResult("剩余量不能小于总数量");
            }
        }

        if (pic != null && !StringUtils.isEmpty(gift.getImage())) {
            UploadUtil.deleteFile(ctx, gift.getImage());
        }

        if (pic != null) {
            System.out.println(ctx);
            // 执行文件上传,获取到保存文件在项目中的相对路径
            String imagePath = UploadUtil.upload(pic, ctx.getRealPath("/upload"));
            gift.setImage(imagePath);
        }

        try {
            if (gift.getId() == null) {
                giftService.insert(gift);
            } else {
                giftService.updateByPrimaryKey(gift);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("保存失败");
        }
        return new JsonResult();
    }

    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Long id, String imagePath) {
        try {

            giftService.deleteByPrimaryKey(id);
            UploadUtil.deleteFile(ctx, imagePath);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("删除失败");
        }

        return new JsonResult();
    }
}

